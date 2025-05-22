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
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.ssl.pem.PemContent;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Getter
@Setter
abstract class AbstractRSAAlgorithmFactoryBean implements FactoryBean<Algorithm> {

	private String certificatePemContent;
	private String privateKeyPemContent;
	private String privateKeyPassword;

	protected final RSAPublicKey getPublicKey() {
		var pemContent = PemContent.of(certificatePemContent);
		return (RSAPublicKey) pemContent.getCertificates().get(0).getPublicKey();
	}

	protected final RSAPrivateKey getPrivateKey() {
		var pemContent = PemContent.of(privateKeyPemContent);
		return (RSAPrivateKey) pemContent.getPrivateKey(privateKeyPassword);
	}

	@Override
	public final Class<?> getObjectType() {
		return Algorithm.class;
	}

}
