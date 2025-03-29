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

import com.github.yingzhuo.turbocharger.security.token.TokenResolver;
import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 应卓
 * @see BasicAuthenticationFilter
 * @see TokenAuthenticationFilter
 * @see RequestMatcher
 * @since 1.2.3
 */
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

	@Nullable
	protected TokenResolver tokenResolver;

	@Nullable
	protected TokenBlacklistManager tokenBlacklistManager;

	@Nullable
	protected RememberMeServices rememberMeServices;

	@Nullable
	protected AuthenticationEntryPoint authenticationEntryPoint;

	@Nullable
	protected ApplicationEventPublisher applicationEventPublisher;

	protected final boolean authenticationIsRequired() {
		final Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}
		return (existingAuth instanceof AnonymousAuthenticationToken);
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  Authentication authResult) {
		// noop
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
												AuthenticationException failed) {
		// noop
	}

	public final void setTokenResolver(TokenResolver tokenResolver) {
		this.tokenResolver = tokenResolver;
	}

	public void setTokenBlacklistManager(@Nullable TokenBlacklistManager tokenBlacklistManager) {
		this.tokenBlacklistManager = tokenBlacklistManager;
	}

	public final void setRememberMeServices(@Nullable RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}

	public final void setAuthenticationEntryPoint(@Nullable AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public void setApplicationEventPublisher(@Nullable ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
