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
package com.github.yingzhuo.turbocharger.keystore;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see KeyStorage
 * @see KeyStorage
 * @since 3.5.0
 */
public class KeyStorageFactoryBean implements FactoryBean<KeyStorage>, InitializingBean {

	private String location;
	private String storepass;
	private KeyStoreFormat keyStoreFormat = KeyStoreFormat.PKCS12;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KeyStorage getObject() throws Exception {
		return switch (keyStoreFormat) {
			case PKCS12 -> KeyStorage.load(location, KeyStoreFormat.PKCS12, storepass);
			case JKS -> KeyStorage.load(location, KeyStoreFormat.JKS, storepass);
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return KeyStorage.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(location, "location must not be null");
		Assert.hasText(storepass, "storepass must not be null or blank");
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}

	public void setKeyStoreFormat(KeyStoreFormat keyStoreFormat) {
		this.keyStoreFormat = keyStoreFormat;
	}
}
