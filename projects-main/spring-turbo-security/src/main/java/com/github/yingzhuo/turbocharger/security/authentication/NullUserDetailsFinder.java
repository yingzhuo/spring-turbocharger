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

import com.github.yingzhuo.turbocharger.security.user.UserDetailsPlus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 应卓
 * @see #getInstance()
 * @see UserDetails
 * @see UserDetailsPlus
 * @since 1.2.3
 */
public final class NullUserDetailsFinder implements UserDetailsFinder {

	/**
	 * 私有构造方法
	 */
	private NullUserDetailsFinder() {
	}

	/**
	 * 获取单例的实例
	 *
	 * @return 实例
	 */
	public static NullUserDetailsFinder getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws AuthenticationException {
		throw new UsernameNotFoundException(username);
	}

	@Nullable
	@Override
	public UserDetails loadUserByUsernameAndPassword(String username, String password) throws AuthenticationException {
		return null;
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final NullUserDetailsFinder INSTANCE = new NullUserDetailsFinder();
	}

}
