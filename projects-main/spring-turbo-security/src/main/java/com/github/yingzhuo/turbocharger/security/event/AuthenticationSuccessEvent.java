/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 认证成功事件
 *
 * @author 应卓
 * @since 2.0.5
 */
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
