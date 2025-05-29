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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.turbocharger.keystore.KeyStoreFormat;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.loadResourceAsInputStream;
import static com.github.yingzhuo.turbocharger.keystore.util.KeyStoreUtils.getSecretKey;
import static com.github.yingzhuo.turbocharger.keystore.util.KeyStoreUtils.loadKeyStore;

/**
 * @author 应卓
 * @since 3.5.0
 */
public class HmacStoreAlgorithmFactoryBean implements FactoryBean<Algorithm> {

	@Setter
	private KeyStoreFormat format = KeyStoreFormat.PKCS12;

	@Setter
	private String location;

	@Setter
	private String storepass;

	@Setter
	private String alias;

	@Setter
	private String keypass;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Algorithm getObject() throws Exception {
		var keyStore = loadKeyStore(loadResourceAsInputStream(location), format, storepass);
		var secretKey = getSecretKey(keyStore, alias, keypass);

		var algName = secretKey.getAlgorithm();
		return switch (algName) {
			case "HmacSHA256" -> Algorithm.HMAC256(secretKey.getEncoded());
			case "HmacSHA384" -> Algorithm.HMAC384(secretKey.getEncoded());
			case "HmacSHA512" -> Algorithm.HMAC512(secretKey.getEncoded());
			default -> throw new IllegalArgumentException("Unsupported algorithm: " + algName);
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return Algorithm.class;
	}

}
