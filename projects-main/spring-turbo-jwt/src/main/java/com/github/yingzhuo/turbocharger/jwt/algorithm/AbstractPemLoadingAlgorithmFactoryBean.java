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
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.readResourceAsString;
import static com.github.yingzhuo.turbocharger.util.StringUtils.isBlank;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Getter
@Setter
public abstract class AbstractPemLoadingAlgorithmFactoryBean implements FactoryBean<Algorithm> {

	private String certificatePemContent;
	private String privateKeyPemContent;
	private String certificatePemLocation;
	private String privateKeyPemLocation;
	private @Nullable String privateKeyPassword;

	/**
	 * 构造方法
	 */
	protected AbstractPemLoadingAlgorithmFactoryBean() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract Algorithm getObject();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return Algorithm.class;
	}

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	protected final PublicKey getPublicKey() {
		var certificatePemContentToUse = getCertificatePemContent();
		if (isBlank(certificatePemContentToUse)) {
			certificatePemContentToUse = readResourceAsString(certificatePemLocation);
		}

		var pemContent = PemContent.of(certificatePemContentToUse);
		var certificates = pemContent.getCertificates();
		if (certificates.size() > 1) {
			throw new IllegalArgumentException("pem contains more than one certificate");
		}
		return certificates.get(0).getPublicKey();
	}

	/**
	 * 获取私钥
	 *
	 * @return 私钥
	 */
	protected final PrivateKey getPrivateKey() {
		var privateKeyPemContentToUse = getPrivateKeyPemContent();
		if (isBlank(privateKeyPemContentToUse)) {
			privateKeyPemContentToUse = readResourceAsString(privateKeyPemLocation);
		}

		var pemContent = PemContent.of(privateKeyPemContentToUse);
		return pemContent.getPrivateKey(privateKeyPassword);
	}

}
