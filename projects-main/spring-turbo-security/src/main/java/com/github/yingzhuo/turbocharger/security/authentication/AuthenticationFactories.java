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
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 应卓
 * @since 3.5.3
 */
public final class AuthenticationFactories {

	/**
	 * 私有构造方法
	 */
	private AuthenticationFactories() {
		super();
	}

	public static Authentication create(UserDetails userDetails, boolean authenticated, @Nullable Token token, @Nullable Object details) {
		var auth = new GenericAuthentication(userDetails, token, details);
		auth.setAuthenticated(authenticated);
		return auth;
	}

	public static Authentication create(Object principal, boolean authenticated, String... authorities) {
		return new AbstractAuthenticationToken(AuthorityUtils.createAuthorityList(authorities)) {
			@Override
			public Object getCredentials() {
				return String.valueOf(System.identityHashCode(this));
			}

			@Override
			public Object getPrincipal() {
				return principal;
			}

			@Override
			public boolean isAuthenticated() {
				return authenticated;
			}
		};
	}

}
