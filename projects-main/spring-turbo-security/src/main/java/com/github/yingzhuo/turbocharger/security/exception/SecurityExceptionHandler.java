package com.github.yingzhuo.turbocharger.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;

import java.io.IOException;

public interface SecurityExceptionHandler
	extends AuthenticationEntryPoint, AccessDeniedHandler, RequestRejectedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   RequestRejectedException requestRejectedException) throws IOException, ServletException;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authenticationException) throws IOException, ServletException;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException;

}
