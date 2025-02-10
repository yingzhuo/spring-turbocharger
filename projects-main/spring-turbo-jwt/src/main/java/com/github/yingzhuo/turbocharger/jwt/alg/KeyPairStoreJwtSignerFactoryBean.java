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

import com.github.yingzhuo.turbocharger.util.crypto.bundle.KeyStoreAsymmetricKeyBundleFactoryBean;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 应卓
 * @see KeyPairJwtSigner
 * @since 3.3.2
 */
public class KeyPairStoreJwtSignerFactoryBean implements FactoryBean<KeyPairJwtSigner>, InitializingBean {

	private final KeyStoreAsymmetricKeyBundleFactoryBean innerFactory = new KeyStoreAsymmetricKeyBundleFactoryBean();

	/**
	 * 默认构造方法
	 */
	public KeyPairStoreJwtSignerFactoryBean() {
		this.setFormat(KeyStoreFormat.PKCS12);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KeyPairJwtSigner getObject() throws Exception {
		innerFactory.afterPropertiesSet();
		var bundle = innerFactory.getObject();
		return new KeyPairJwtSigner(bundle.getKeyPair());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.innerFactory.afterPropertiesSet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return KeyPairJwtSigner.class;
	}

	public void setLocation(String keyStoreLocation) {
		innerFactory.setLocation(keyStoreLocation);
	}

	public void setFormat(KeyStoreFormat keyStoreFormat) {
		innerFactory.setFormat(keyStoreFormat);
	}

	public void setStorepass(String storepass) {
		innerFactory.setStorepass(storepass);
	}

	public void setAlias(String alias) {
		innerFactory.setAlias(alias);
	}

	public void setKeypass(String keypass) {
		innerFactory.setKeypass(keypass);
	}

}
