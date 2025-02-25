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

import com.github.yingzhuo.turbocharger.security.filter.BasicAuthenticationFilter;
import com.github.yingzhuo.turbocharger.security.user.UserDetailsPlus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 通过用户名和密码查找{@link UserDetails}实例的部件
 *
 * @author 应卓
 * @see TokenToUserConverter
 * @see BasicAuthenticationFilter
 * @see UserDetails
 * @see UserDetailsPlus
 * @since 1.2.3
 */
public interface UserDetailsFinder {

	@NonNull
	public UserDetails loadUserByUsername(String username) throws AuthenticationException;

	@Nullable
	public UserDetails loadUserByUsernameAndPassword(String username, String password) throws AuthenticationException;

}
