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
package com.github.yingzhuo.turbocharger.security.encoder.hutool;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.github.yingzhuo.turbocharger.security.encoder.EncodingIds;
import com.github.yingzhuo.turbocharger.security.encoder.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SHA-384 算法实现 {@link PasswordEncoder}
 *
 * @author 应卓
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @see PasswordEncoderFactories
 * @see EncodingIds#SHA_384
 * @since 1.0.1
 */
public final class SHA384PasswordEncoder implements PasswordEncoder {

	/**
	 * 构造方法
	 */
	public SHA384PasswordEncoder() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		final Digester digester = new Digester(DigestAlgorithm.SHA384);
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
