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
package com.github.yingzhuo.turbocharger.security.passwordencoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.yingzhuo.turbocharger.util.StringUtils.isNotBlank;
import static com.github.yingzhuo.turbocharger.util.reflection.InstanceUtils.newInstance;

/**
 * {@link PasswordEncoder} 创建工具
 *
 * @author 应卓
 * @see PasswordEncoder
 * @see DelegatingPasswordEncoder
 * @see EncodingIds
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings("deprecation")
public final class PasswordEncoderFactories {

	/**
	 * 私有构造方法
	 */
	private PasswordEncoderFactories() {
		super();
	}

	public static BCryptPasswordEncoder createBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static DelegatingPasswordEncoder createDelegatingPasswordEncoder() {
		return createDelegatingPasswordEncoder(EncodingIds.bcrypt, EncodingIds.noop);
	}

	public static DelegatingPasswordEncoder createDelegatingPasswordEncoder(String encodingId) {
		return createDelegatingPasswordEncoder(encodingId, EncodingIds.noop);
	}

	public static DelegatingPasswordEncoder createDelegatingPasswordEncoder(
		String encodingId,
		@Nullable String defaultPasswordEncoderForMatches) {

		Assert.hasText(encodingId, "encodingId is required");

		var encodersMap = getEncoders();
		var ret = new DelegatingPasswordEncoder(encodingId, encodersMap);

		if (log.isInfoEnabled()) {
			var ids = encodersMap.keySet();
			log.info("supported encoder ids: [{}]", String.join(",", ids));
		}

		if (isNotBlank(defaultPasswordEncoderForMatches)) {
			ret.setDefaultPasswordEncoderForMatches(encodersMap.get(defaultPasswordEncoderForMatches));
		}

		return ret;
	}

	private static Map<String, PasswordEncoder> getEncoders() {
		var map = new HashMap<String, PasswordEncoder>();
		map.put(EncodingIds.bcrypt, new BCryptPasswordEncoder());
		map.put(EncodingIds.noop, NoOpPasswordEncoder.getInstance());
		map.put(EncodingIds.ldap, new LdapShaPasswordEncoder());
		map.put(EncodingIds.MD4, new Md4PasswordEncoder());
		map.put(EncodingIds.MD5, new MessageDigestPasswordEncoder("MD5"));
		map.put(EncodingIds.SHA_1, new MessageDigestPasswordEncoder("SHA-1"));
		map.put(EncodingIds.SHA_256, new MessageDigestPasswordEncoder("SHA-256"));
		map.put(EncodingIds.pbkdf2, Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		map.put(EncodingIds.scrypt, SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		map.put(EncodingIds.argon2, Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

		Optional<PasswordEncoder> encoderOp;

		// MD2
		encoderOp = loadInstance("com.github.yingzhuo.turbocharger.security.passwordencoder.hutool.MD2PasswordEncoder");
		encoderOp.ifPresent(e -> map.put(EncodingIds.MD2, e));

		// SHA384
		encoderOp = loadInstance("com.github.yingzhuo.turbocharger.security.passwordencoder.hutool.SHA384PasswordEncoder");
		encoderOp.ifPresent(e -> map.put(EncodingIds.SHA_384, e));

		// SHA512
		encoderOp = loadInstance("com.github.yingzhuo.turbocharger.security.passwordencoder.hutool.SHA512PasswordEncoder");
		encoderOp.ifPresent(e -> map.put(EncodingIds.SHA_512, e));

		// SM3
		encoderOp = loadInstance("com.github.yingzhuo.turbocharger.security.passwordencoder.hutool.SM3PasswordEncoder");
		encoderOp.ifPresent(e -> map.put(EncodingIds.SM3, e));

		return Collections.unmodifiableMap(map);
	}

	private static Optional<PasswordEncoder> loadInstance(String classname) {
		try {
			return newInstance(classname);
		} catch (Throwable ignored) {
			// 加载失败的主要原因:
			// 1. 没有默认构造方法
			// 2. 缺少依赖
			return Optional.empty();
		}
	}

}
