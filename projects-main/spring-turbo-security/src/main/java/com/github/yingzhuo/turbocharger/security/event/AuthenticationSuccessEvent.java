package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationSuccessEvent extends AbstractSecurityEvent {

	private final Collection<? extends GrantedAuthority> authorities;

	public AuthenticationSuccessEvent(HttpServletRequest request, @Nullable HttpServletResponse response, Collection<? extends GrantedAuthority> authorities) {
		super(request, response);
		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

}
