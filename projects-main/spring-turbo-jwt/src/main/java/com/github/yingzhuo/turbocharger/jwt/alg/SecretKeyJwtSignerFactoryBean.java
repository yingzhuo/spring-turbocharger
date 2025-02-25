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
package com.github.yingzhuo.turbocharger.jwt.alg;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import static com.github.yingzhuo.turbocharger.jwt.alg.JwtSignerFactories.createFromBase64EncodedString;
import static com.github.yingzhuo.turbocharger.jwt.alg.JwtSignerFactories.createFromBase64URlEncodedString;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author 应卓
 * @see SecretKeyJwtSigner
 * @see KeyPairPemJwtSignerFactoryBean
 * @see KeyPairStoreJwtSignerFactoryBean
 * @since 3.3.2
 */
public class SecretKeyJwtSignerFactoryBean implements FactoryBean<SecretKeyJwtSigner>, InitializingBean {

	@Nullable
	private String base64EncodedString;

	@Nullable
	private String base64URLEncodedString;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecretKeyJwtSigner getObject() {
		if (hasText(base64EncodedString)) {
			return createFromBase64EncodedString(base64EncodedString);
		}

		if (hasText(base64URLEncodedString)) {
			return createFromBase64URlEncodedString(base64URLEncodedString);
		}

		throw new IllegalStateException("invalid configuration");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return SecretKeyJwtSigner.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		// noop
	}

	public void setBase64EncodedString(@Nullable String base64EncodedString) {
		this.base64EncodedString = base64EncodedString;
	}

	public void setBase64URLEncodedString(@Nullable String base64URLEncodedString) {
		this.base64URLEncodedString = base64URLEncodedString;
	}

}
