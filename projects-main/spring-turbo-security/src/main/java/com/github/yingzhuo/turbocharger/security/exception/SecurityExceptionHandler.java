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

/**
 * @author 应卓
 * @see AuthenticationEntryPoint
 * @see AccessDeniedHandler
 * @see RequestRejectedHandler
 * @see org.springframework.security.web.firewall.HttpFirewall
 * @since 1.2.3
 */
public interface SecurityExceptionHandler
	extends AuthenticationEntryPoint, AccessDeniedHandler, RequestRejectedHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   RequestRejectedException requestRejectedException) throws IOException, ServletException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authenticationException) throws IOException, ServletException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException;

}
