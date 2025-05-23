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
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreHelper;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.loadResourceAsInputStream;
import static com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreHelper.loadKeyStore;

/**
 * @author 应卓
 * @see KeyStoreHelper
 * @since 3.5.0
 */
public abstract class AbstractKeyStoreLoadingAlgorithmFactoryBean implements FactoryBean<Algorithm>, InitializingBean {

	private final KeyStoreFormat keyStoreFormat;

	@Setter
	private String location;

	@Setter
	private String storepass;

	@Setter
	private String alias;

	@Setter
	private String keypass;

	@Nullable
	private KeyStore keyStore = null;

	/**
	 * 构造方法
	 *
	 * @param keyStoreFormat KeyStore类型
	 */
	protected AbstractKeyStoreLoadingAlgorithmFactoryBean(KeyStoreFormat keyStoreFormat) {
		this.keyStoreFormat = keyStoreFormat;
	}

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
		keyStore = loadKeyStore(loadResourceAsInputStream(location), keyStoreFormat, storepass);
	}

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	public final PublicKey getPublicKey() {
		Assert.notNull(keyStore, "The keystore is not loaded");
		return KeyStoreHelper.getPublicKey(keyStore, alias);
	}

	/**
	 * 获取私钥
	 *
	 * @return 私钥
	 */
	public final PrivateKey getPrivateKey() {
		Assert.notNull(keyStore, "The keystore is not loaded");
		return KeyStoreHelper.getPrivateKey(keyStore, alias, keypass);
	}

}
