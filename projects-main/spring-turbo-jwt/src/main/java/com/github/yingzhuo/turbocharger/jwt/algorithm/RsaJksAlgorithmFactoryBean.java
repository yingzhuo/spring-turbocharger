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
import lombok.Setter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Setter
public class RsaJksAlgorithmFactoryBean extends AbstractJksLoadingAlgorithmFactoryBean {

	private AlgorithmType algorithmType = AlgorithmType.RSA_512;

	/**
	 * 默认构造方法
	 */
	public RsaJksAlgorithmFactoryBean() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("DuplicatedCode")
	public Algorithm getObject() {
		var publicKey = (RSAPublicKey) getPublicKey();
		var privateKey = (RSAPrivateKey) getPrivateKey();

		return switch (algorithmType) {
			case RSA_256 -> Algorithm.RSA256(publicKey, privateKey);
			case RSA_384 -> Algorithm.RSA384(publicKey, privateKey);
			case RSA_512 -> Algorithm.RSA512(publicKey, privateKey);
			default -> throw new IllegalStateException("Unsupported algorithm type: " + algorithmType);
		};
	}

}
