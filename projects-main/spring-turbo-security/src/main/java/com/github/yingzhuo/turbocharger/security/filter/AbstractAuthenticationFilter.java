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
