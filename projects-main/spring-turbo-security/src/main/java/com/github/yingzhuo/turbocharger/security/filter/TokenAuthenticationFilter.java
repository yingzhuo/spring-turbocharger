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

import com.github.yingzhuo.turbocharger.security.authentication.GenericAuthentication;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationFailureEvent;
import com.github.yingzhuo.turbocharger.security.event.AuthenticationSuccessEvent;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.security.token.resolver.BearerTokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 基于令牌的认证过滤器
 *
 * @author 应卓
 * @since 1.0.0
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	private TokenToUserConverter tokenToUserConverter = token -> null;

	/**
	 * 构造方法
	 */
	public TokenAuthenticationFilter() {
		super.setTokenResolver(new BearerTokenResolver());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// 如果之前的过滤器已经完成认证了
		// 则本过滤不工作
		if (!authenticationIsRequired()) {
			filterChain.doFilter(request, response);
			return;
		}

		try {

			// 尝试解析令牌
			final Token token = tokenResolver.resolve(new ServletWebRequest(request)).orElse(null);

			if (token == null) {
				log.trace("token cannot be resolved");
				filterChain.doFilter(request, response);
				return;
			}

			// 检查黑名单
			if (this.tokenBlacklistManager != null) {
				this.tokenBlacklistManager.verify(token);
			}

			// 尝试获取用户对象
			final UserDetails user = tokenToUserConverter.convert(token);
			if (user == null) {
				log.trace("cannot convert token to UserDetails instance");
				filterChain.doFilter(request, response);
				return;
			} else {
				if (log.isTraceEnabled()) {
					log.trace("UserDetails converted. (username: {})", user.getUsername());
				}
			}

			// 构建认证对象
			var authentication = new GenericAuthentication(
				user,
				token,
				new WebAuthenticationDetailsSource().buildDetails(request) // details
			);
			authentication.setAuthenticated(true);

			// 设置认证上下文
			var securityContext = securityContextHolderStrategy.getContext();
			securityContext.setAuthentication(authentication);

			// 发布认证成功通知 (可选)
			if (this.applicationEventPublisher != null) {
				var event = new AuthenticationSuccessEvent(authentication, token);
				event.setRequest(request);
				event.setResponse(response);
				this.applicationEventPublisher.publishEvent(event);
			}

		} catch (AuthenticationException e) {

			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}

			securityContextHolderStrategy.clearContext();

			// 发布认证失败通知 (可选)
			if (this.applicationEventPublisher != null) {
				var event = new AuthenticationFailureEvent(e);
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

	public void setTokenToUserConverter(TokenToUserConverter converter) {
		this.tokenToUserConverter = converter;
	}

}
