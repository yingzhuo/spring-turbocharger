/*
 *
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.yingzhuo.turbocharger.security.filter;

import com.github.yingzhuo.turbocharger.security.authentication.NullUserDetailsFinder;
import com.github.yingzhuo.turbocharger.security.authentication.TokenAuthentication;
import com.github.yingzhuo.turbocharger.security.authentication.UserDetailsFinder;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationFailureEvent;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationSuccessEvent;
import com.github.yingzhuo.turbocharger.security.filter.factory.BasicAuthenticationFilterFactoryBean;
import com.github.yingzhuo.turbocharger.security.token.BasicToken;
import com.github.yingzhuo.turbocharger.security.token.BasicTokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * HttpBasic认证过滤器
 *
 * <p>
 * Spring Security 已经提供了此功能。但是笔者认为其严重设计过度，不是特别习惯使用 {@link org.springframework.security.web.authentication.www.BasicAuthenticationFilter}。
 * 故此，设计了一个简化版本的过滤器。
 * </p>
 *
 * @author 应卓
 * @see TokenAuthenticationFilter
 * @see BasicAuthenticationFilterFactoryBean
 * @since 1.2.3
 */
public class BasicAuthenticationFilter extends AbstractAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(BasicAuthenticationFilter.class);

	@Nullable
	private UserDetailsFinder userDetailsFinder = NullUserDetailsFinder.getInstance();

	/**
	 * 构造方法
	 */
	public BasicAuthenticationFilter() {
		super.setTokenResolver(new BasicTokenResolver());
	}

	@Override
	@SuppressWarnings("DuplicatedCode")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// 其他过滤器已经做完了认证工作则跳过
		if (!super.authenticationIsRequired()) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			var token = tokenResolver.resolve(new ServletWebRequest(request, response)).orElse(null);

			if (this.tokenBlacklistManager != null && token != null) {
				this.tokenBlacklistManager.verify(token);
			}

			if (!(token instanceof BasicToken basicToken)) {
				filterChain.doFilter(request, response);
				return;
			}

			final String username = basicToken.getUsername();
			final String password = basicToken.getPassword();

			final UserDetails user = userDetailsFinder.loadUserByUsernameAndPassword(username, password);
			if (user == null) {
				filterChain.doFilter(request, response);
				return;
			}

			var authentication = new TokenAuthentication(user, token);
			authentication.setAuthenticated(true);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (this.rememberMeServices != null) {
				rememberMeServices.loginSuccess(request, response, authentication);
			}

			onSuccessfulAuthentication(request, response, authentication);

			if (this.applicationEventPublisher != null) {
				final var event = new AuthenticationSuccessEvent(authentication, token);
				event.setRequest(request);
				event.setResponse(response);
				this.applicationEventPublisher.publishEvent(event);
			}

		} catch (AuthenticationException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}

			SecurityContextHolder.clearContext();

			if (rememberMeServices != null) {
				rememberMeServices.loginFail(request, response);
			}

			onUnsuccessfulAuthentication(request, response, e);

			if (this.applicationEventPublisher != null) {
				final var event = new AuthenticationFailureEvent(e);
				event.setRequest(request);
				event.setResponse(response);
				this.applicationEventPublisher.publishEvent(event);
			}

			if (authenticationEntryPoint != null) {
				authenticationEntryPoint.commence(request, response, e);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	public void setUserDetailsFinder(UserDetailsFinder userDetailsFinder) {
		this.userDetailsFinder = userDetailsFinder;
	}

}
