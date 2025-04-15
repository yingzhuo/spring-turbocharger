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

import com.github.yingzhuo.turbocharger.util.crypto.bundle.PemAsymmetricKeyBundleFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 应卓
 * @see KeyPairJwtSigner
 * @since 3.3.2
 */
public class KeyPairPemJwtSignerFactoryBean implements FactoryBean<KeyPairJwtSigner>, InitializingBean {

	private final PemAsymmetricKeyBundleFactoryBean delegatingFactory = new PemAsymmetricKeyBundleFactoryBean();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KeyPairJwtSigner getObject() {
		delegatingFactory.afterPropertiesSet();
		var bundle = delegatingFactory.getObject();
		return new KeyPairJwtSigner(bundle.getKeyPair());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		delegatingFactory.afterPropertiesSet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return KeyPairJwtSigner.class;
	}

	public void setCertificateLocation(String certificateLocation) {
		delegatingFactory.setCertificateLocation(certificateLocation);
	}

	public void setPrivateKeyLocation(String privateKeyLocation) {
		delegatingFactory.setPrivateKeyLocation(privateKeyLocation);
	}

	public void setCertificateContent(String certificateContent) {
		delegatingFactory.setCertificateContent(certificateContent);
	}

	public void setPrivateKeyContent(String privateKeyContent) {
		delegatingFactory.setPrivateKeyContent(privateKeyContent);
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		delegatingFactory.setPrivateKeyPassword(privateKeyPassword);
	}

}
