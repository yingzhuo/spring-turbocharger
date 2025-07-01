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
public final class PasswordEncoderFactories implements EncodingIds {

	/**
	 * 私有构造方法
	 */
	private PasswordEncoderFactories() {
		super();
	}

	/**
	 * 创建 {@link BCryptPasswordEncoder} 实例
	 *
	 * @return 实例
	 */
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
			log.info("supported encoder ids: [{}]", String.join(", ", ids));
		}

		if (isNotBlank(defaultPasswordEncoderForMatches)) {
			ret.setDefaultPasswordEncoderForMatches(encodersMap.get(defaultPasswordEncoderForMatches));
		}

		return ret;
	}

	@SuppressWarnings("deprecation")
	private static Map<String, PasswordEncoder> getEncoders() {
		var map = new HashMap<String, PasswordEncoder>();
		map.put(bcrypt, new BCryptPasswordEncoder());
		map.put(noop, NoOpPasswordEncoder.getInstance());
		map.put(ldap, new LdapShaPasswordEncoder());
		map.put(MD4, new Md4PasswordEncoder());
		map.put(MD5, new MessageDigestPasswordEncoder("MD5"));
		map.put(SHA_1, new MessageDigestPasswordEncoder("SHA-1"));
		map.put(SHA_256, new MessageDigestPasswordEncoder("SHA-256"));
		map.put(pbkdf2, Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		map.put(scrypt, SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		map.put(argon2, Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

		// SM3 (弹性加载)
		var encoderOp = loadInstance("com.github.yingzhuo.turbocharger.security.passwordencoder.hutool.SM3PasswordEncoder");
		encoderOp.ifPresent(e -> map.put(SM3, e));
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
