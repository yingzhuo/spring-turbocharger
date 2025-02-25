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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.3.1
 */
@SuppressWarnings("deprecation")
public class UserDetailsServiceUserDetailsFinder implements UserDetailsFinder {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public UserDetailsServiceUserDetailsFinder(UserDetailsService userDetailsService) {
		this(userDetailsService, NoOpPasswordEncoder.getInstance());
	}

	public UserDetailsServiceUserDetailsFinder(UserDetailsService userDetailsService,
											   @Nullable PasswordEncoder passwordEncoder) {
		Assert.notNull(userDetailsService, "userDetailsService is required");
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = Objects.requireNonNullElseGet(passwordEncoder, NoOpPasswordEncoder::getInstance);
	}

	@NonNull
	@Override
	public UserDetails loadUserByUsername(String username) throws AuthenticationException {
		return userDetailsService.loadUserByUsername(username);
	}

	@Nullable
	@Override
	public UserDetails loadUserByUsernameAndPassword(String username, String password) throws AuthenticationException {
		try {
			var ud = userDetailsService.loadUserByUsername(username);
			if (ud == null || ud.getPassword() == null) {
				return null;
			}
			return passwordEncoder.matches(password, ud.getPassword()) ? ud : null;
		} catch (UsernameNotFoundException e) {
			return null;
		}
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

}
