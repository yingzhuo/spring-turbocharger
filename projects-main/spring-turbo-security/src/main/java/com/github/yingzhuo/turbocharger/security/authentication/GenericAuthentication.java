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
package com.github.yingzhuo.turbocharger.security.authentication;

import com.github.yingzhuo.turbocharger.security.token.Token;
import org.springframework.core.style.ToStringCreator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * 通用型 {@link Authentication}
 *
 * @author 应卓
 * @since 3.5.3
 */
public class GenericAuthentication implements Authentication, Serializable, Principal {

	private final @NonNull UserDetails userDetails;
	private final @Nullable Token token;
	private boolean authenticated = true;
	private @Nullable Object details;

	/**
	 * 构造方法
	 *
	 * @param userDetails 用户
	 */
	public GenericAuthentication(UserDetails userDetails) {
		this(userDetails, null, null);
	}

	/**
	 * 构造方法
	 *
	 * @param userDetails 用户
	 * @param token       令牌
	 */
	public GenericAuthentication(UserDetails userDetails, @Nullable Token token) {
		this(userDetails, token, null);
	}

	/**
	 * 构造方法
	 *
	 * @param userDetails 用户
	 * @param token       令牌
	 * @param details     其他认证信息
	 */
	public GenericAuthentication(UserDetails userDetails, @Nullable Token token, @Nullable Object details) {
		Assert.notNull(userDetails, "userDetails must not be null");
		this.userDetails = userDetails;
		this.token = token;
		this.details = details;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDetails.getAuthorities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getCredentials() {
		return token != null ? token.asString() : String.valueOf(System.identityHashCode(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Object getDetails() {
		return this.details;
	}

	/**
	 * 设置其他认证信息
	 *
	 * @param details 其他认证信息
	 */
	public void setDetails(@Nullable Object details) {
		this.details = details;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return userDetails.getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var creator = new ToStringCreator(this);
		creator.append("Authentication").append(" [");
		creator.append("Principal=").append(getPrincipal()).append(", ");
		creator.append("Credentials=[PROTECTED], ");
		creator.append("Authenticated=").append(isAuthenticated()).append(", ");
		creator.append("Granted Authorities=").append(getAuthorities());
		creator.append("]");
		return creator.toString();
	}

}
