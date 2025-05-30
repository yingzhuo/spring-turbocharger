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

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
public class EcdsaStoreAlgorithmFactoryBean extends AbstractStoreAlgorithmFactoryBean {

	@Setter
	private AlgorithmType algorithmType = AlgorithmType.ECDSA512;

	/**
	 * 默认构造方法
	 */
	public EcdsaStoreAlgorithmFactoryBean() {
		this(KeyStoreFormat.PKCS12);
	}

	/**
	 * 构造方法
	 *
	 * @param keyStoreFormat KeyStore格式
	 */
	public EcdsaStoreAlgorithmFactoryBean(KeyStoreFormat keyStoreFormat) {
		super.setFormat(keyStoreFormat);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("DuplicatedCode")
	public Algorithm getObject() {
		var publicKey = (ECPublicKey) getBundle().getPublicKey();
		var privateKey = (ECPrivateKey) getBundle().getPrivateKey();

		return switch (algorithmType) {
			case ECDSA256 -> Algorithm.ECDSA256(publicKey, privateKey);
			case ECDSA384 -> Algorithm.ECDSA384(publicKey, privateKey);
			case ECDSA512 -> Algorithm.ECDSA512(publicKey, privateKey);
			default -> throw new IllegalArgumentException("Unsupported algorithm type: " + algorithmType);
		};
	}

}
