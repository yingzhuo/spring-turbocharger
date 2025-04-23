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
package com.github.yingzhuo.turbocharger.security.event;

import com.github.yingzhuo.turbocharger.security.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;

/**
 * 认证成功事件
 *
 * @author 应卓
 * @since 2.0.5
 */
public class AuthenticationSuccessEvent extends ApplicationEvent {

	@Nullable
	private final Token token;

	@Nullable
	private HttpServletRequest request;

	@Nullable
	private HttpServletResponse response;

	public AuthenticationSuccessEvent(Authentication source, @Nullable Token token) {
		super(source);
		this.token = token;
	}

	public Authentication getAuthentication() {
		return (Authentication) super.getSource();
	}

	@Nullable
	public Token getToken() {
		return token;
	}

	@Nullable
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(@Nullable HttpServletRequest request) {
		this.request = request;
	}

	@Nullable
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(@Nullable HttpServletResponse response) {
		this.response = response;
	}

}
