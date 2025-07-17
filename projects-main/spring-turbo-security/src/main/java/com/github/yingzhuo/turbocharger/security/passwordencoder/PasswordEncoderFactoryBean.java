/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.passwordencoder;

import com.github.yingzhuo.turbocharger.util.spi.SPILoader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 3.5.3
 */
public class PasswordEncoderFactoryBean implements FactoryBean<PasswordEncoder>, EncodingIds {

	private boolean enableDefaultEncoders = true;
	private String idForEncode = bcrypt;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordEncoder getObject() {
		var encodersInUse = enableDefaultEncoders ? getDefaultEncoders() : new HashMap<String, PasswordEncoder>();

		SPILoader.getDefault(PasswordEncoderProvider.class)
			.load()
			.forEach(p -> encodersInUse.put(p.getId(), p.getEncoder()));

		if (encodersInUse.isEmpty()) {
			return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
		} else {
			return new DelegatingPasswordEncoder(idForEncode, encodersInUse);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return PasswordEncoder.class;
	}

	@SuppressWarnings("deprecation")
	private Map<String, PasswordEncoder> getDefaultEncoders() {
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
		return map;
	}

	public void setEnableDefaultEncoders(boolean enableDefaultEncoders) {
		this.enableDefaultEncoders = enableDefaultEncoders;
	}

	public void setIdForEncode(String idForEncode) {
		this.idForEncode = idForEncode;
	}

}
