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
package com.github.yingzhuo.turbocharger.security.passwordencoder.hutool;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 国密算法 (SM3)
 *
 * @author 应卓
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @since 1.0.1
 */
public final class SM3PasswordEncoder implements PasswordEncoder {

	/**
	 * 构造方法
	 */
	public SM3PasswordEncoder() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		var digester = DigestUtil.digester("SM3");
		return digester.digestHex(rawPassword.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

}
