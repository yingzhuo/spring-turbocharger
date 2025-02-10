/*
 *
 * Copyright 2022-2025 the original author or authors.
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

	private final PemAsymmetricKeyBundleFactoryBean innerFactory = new PemAsymmetricKeyBundleFactoryBean();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KeyPairJwtSigner getObject() {
		innerFactory.afterPropertiesSet();
		var bundle = innerFactory.getObject();
		return new KeyPairJwtSigner(bundle.getKeyPair());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		this.innerFactory.afterPropertiesSet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return KeyPairJwtSigner.class;
	}

	public void setCertificateLocation(String certificateLocation) {
		innerFactory.setCertificateLocation(certificateLocation);
	}

	public void setPrivateKeyLocation(String privateKeyLocation) {
		innerFactory.setPrivateKeyLocation(privateKeyLocation);
	}

	public void setCertificateContent(String certificateContent) {
		innerFactory.setCertificateContent(certificateContent);
	}

	public void setPrivateKeyContent(String privateKeyContent) {
		innerFactory.setPrivateKeyContent(privateKeyContent);
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		innerFactory.setPrivateKeyPassword(privateKeyPassword);
	}

}
