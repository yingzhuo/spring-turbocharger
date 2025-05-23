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
import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Getter
@Setter
public abstract class AbstractPemLoadingAlgorithmFactoryBean implements FactoryBean<Algorithm>, InitializingBean, DisposableBean {

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
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}

	protected final PublicKey getPublicKey() {
		var certificatePemContentToUse = getCertificatePemContent();
		if (StringUtils.isBlank(certificatePemContentToUse)) {
			certificatePemContentToUse = ResourceUtils.readResourceAsString(certificatePemLocation);
		}

		var pemContent = PemContent.of(certificatePemContentToUse);
		var certificates = pemContent.getCertificates();
		if (certificates.size() > 1) {
			throw new IllegalArgumentException("pem contains more than one certificate");
		}
		return certificates.get(0).getPublicKey();
	}

	protected final PrivateKey getPrivateKey() {
		var privateKeyPemContentToUse = getPrivateKeyPemContent();
		if (StringUtils.isBlank(privateKeyPemContentToUse)) {
			privateKeyPemContentToUse = ResourceUtils.readResourceAsString(privateKeyPemLocation);
		}

		var pemContent = PemContent.of(privateKeyPemContentToUse);
		return pemContent.getPrivateKey(privateKeyPassword);
	}

}
