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

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Setter
public class EcdsaJksAlgorithmFactoryBean extends AbstractJksLoadingAlgorithmFactoryBean {

	private AlgorithmType algorithmType = AlgorithmType.ECDSA_512;

	@Override
	public Algorithm getObject() throws Exception {
		var publicKey = (ECPublicKey) getPublicKey();
		var privateKey = (ECPrivateKey) getPrivateKey();

		return switch (algorithmType) {
			case ECDSA_256 -> Algorithm.ECDSA256(publicKey, privateKey);
			case ECDSA_384 -> Algorithm.ECDSA384(publicKey, privateKey);
			case ECDSA_512 -> Algorithm.ECDSA512(publicKey, privateKey);
			default -> throw new IllegalStateException("Unsupported algorithm type: " + algorithmType);
		};
	}
}
