/*
 * Copyright 2022-2026 the original author or authors.
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
 */
package com.github.yingzhuo.turbocharger.security.filter;

import com.github.yingzhuo.turbocharger.security.authentication.AuthenticationDetailsCreator;
import com.github.yingzhuo.turbocharger.security.token.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.turbocharger.security.token.resolver.TokenResolver;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

/**
 * @author 应卓
 * @see TokenAuthenticationFilter
 * @since 1.2.3
 */
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

	protected SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	protected TokenResolver tokenResolver = request -> Optional.empty();
	protected @Nullable AuthenticationDetailsCreator authenticationDetailsCreator;
	protected @Nullable TokenBlacklistManager tokenBlacklistManager;
	protected @Nullable AuthenticationEntryPoint authenticationEntryPoint;
	protected @Nullable ApplicationEventPublisher applicationEventPublisher;

	protected final boolean authenticationIsRequired() {
		final Authentication existingAuth = securityContextHolderStrategy.getContext().getAuthentication();
		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}
		return (existingAuth instanceof AnonymousAuthenticationToken);
	}

	public final void setTokenResolver(TokenResolver tokenResolver) {
		this.tokenResolver = tokenResolver;
	}

	public void setTokenBlacklistManager(@Nullable TokenBlacklistManager tokenBlacklistManager) {
		this.tokenBlacklistManager = tokenBlacklistManager;
	}

	public final void setAuthenticationEntryPoint(@Nullable AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public final void setApplicationEventPublisher(@Nullable ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public final void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}

	public final void setAuthenticationDetailsCreator(@Nullable AuthenticationDetailsCreator authenticationDetailsCreator) {
		this.authenticationDetailsCreator = authenticationDetailsCreator;
	}

}
