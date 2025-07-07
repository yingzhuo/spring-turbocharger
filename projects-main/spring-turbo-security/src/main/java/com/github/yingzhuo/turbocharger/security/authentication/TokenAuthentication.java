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
package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.security.util.AuthorityUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * 认证对象，本类是 {@link org.springframework.security.core.Authentication} 的实现
 *
 * @author 应卓
 * @see org.springframework.security.core.Authentication
 * @since 1.0.0
 */
public final class TokenAuthentication extends AbstractAuthenticationToken {

	/**
	 * 当前用户
	 */
	@Nullable
	private final UserDetails userDetails;

	/**
	 * 当前认证的令牌 (optional)
	 */
	@Nullable
	private final Token token;

	/**
	 * 默认构造方法
	 */
	public TokenAuthentication() {
		this(null, null);
	}

	/**
	 * 认证对象
	 *
	 * @param userDetails 用户信息
	 */
	public TokenAuthentication(@Nullable UserDetails userDetails) {
		this(userDetails, null);
	}

	/**
	 * 认证对象
	 *
	 * @param userDetails 用户信息
	 * @param token       令牌
	 */
	public TokenAuthentication(@Nullable UserDetails userDetails, @Nullable Token token) {
		super(AuthorityUtils.getAuthorities(userDetails));
		this.userDetails = userDetails;
		this.token = token;
		super.setAuthenticated(userDetails != null);
		super.setDetails(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@NonNull
	@Override
	public Object getPrincipal() {
		return Objects.requireNonNullElse(userDetails, Long.toString(System.identityHashCode(this)));
	}

	/**
	 * {@inheritDoc}
	 */
	@NonNull
	@Override
	public Object getCredentials() {
		if (token != null) {
			return token.asString();
		}
		if (userDetails != null) {
			var pwd = userDetails.getPassword();
			if (pwd != null) {
				return pwd;
			}
		}
		return "";
	}

	@Nullable
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public UserDetails getRequiredUserDetails() {
		var ud = getUserDetails();
		return Objects.requireNonNull(ud);
	}

	@Nullable
	public Token getToken() {
		return token;
	}

	public Token getRequiredToken() {
		var token = getToken();
		return Objects.requireNonNull(token);
	}

}
