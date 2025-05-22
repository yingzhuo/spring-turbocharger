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
import com.github.yingzhuo.turbocharger.util.StringUtils;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Setter
public abstract class AbstractECAlgorithmFactoryBean implements FactoryBean<Algorithm>, InitializingBean {

	private String certificatePemContent;
	private String privateKeyPemContent;

	@Nullable
	private String privateKeyPassword;

	protected final ECPublicKey getPublicKey() {
		var pemContent = PemContent.of(certificatePemContent);
		return (ECPublicKey) pemContent.getCertificates().get(0).getPublicKey();
	}

	protected final ECPrivateKey getPrivateKey() {
		var pemContent = PemContent.of(privateKeyPemContent);
		return (ECPrivateKey) pemContent.getPrivateKey(privateKeyPassword);
	}

	@Override
	public final Class<?> getObjectType() {
		return Algorithm.class;
	}

	@Override
	public void afterPropertiesSet() {
		if (StringUtils.isBlank(privateKeyPassword)) {
			privateKeyPassword = null;
		}
	}
}
