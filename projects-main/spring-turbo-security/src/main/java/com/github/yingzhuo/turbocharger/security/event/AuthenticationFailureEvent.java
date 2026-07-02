package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailureEvent extends AbstractSecurityEvent {

	private final AuthenticationException authenticationException;

	public AuthenticationFailureEvent(HttpServletRequest request, @Nullable HttpServletResponse response, AuthenticationException authenticationException) {
		super(request, response);
		this.authenticationException = authenticationException;
	}

	public AuthenticationException getAuthenticationException() {
		return authenticationException;
	}

}
