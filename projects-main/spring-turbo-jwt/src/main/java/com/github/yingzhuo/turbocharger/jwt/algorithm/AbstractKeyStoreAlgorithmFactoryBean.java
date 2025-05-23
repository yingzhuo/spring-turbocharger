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
import com.github.yingzhuo.turbocharger.util.crypto.bundle.AsymmetricKeyBundle;
import com.github.yingzhuo.turbocharger.util.crypto.bundle.KeyStoreAsymmetricKeyBundleFactoryBean;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreHelper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see KeyStoreHelper
 * @since 3.5.0
 */
public abstract class AbstractKeyStoreAlgorithmFactoryBean implements FactoryBean<Algorithm>, InitializingBean {

	private final KeyStoreAsymmetricKeyBundleFactoryBean delegatingFactoryBean;

	@Nullable
	private AsymmetricKeyBundle bundle;

	/**
	 * 构造方法
	 */
	protected AbstractKeyStoreAlgorithmFactoryBean() {
		delegatingFactoryBean = new KeyStoreAsymmetricKeyBundleFactoryBean();
		delegatingFactoryBean.setFormat(KeyStoreFormat.PKCS12);
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
	public void afterPropertiesSet() throws Exception {
		delegatingFactoryBean.afterPropertiesSet();
		bundle = delegatingFactoryBean.getObject();
	}

	public void setLocation(String location) {
		delegatingFactoryBean.setLocation(location);
	}

	public void setFormat(KeyStoreFormat format) {
		delegatingFactoryBean.setFormat(format);
	}

	public void setStorepass(String storepass) {
		delegatingFactoryBean.setStorepass(storepass);
	}

	public void setAlias(String alias) {
		delegatingFactoryBean.setAlias(alias);
	}

	public void setKeypass(String keypass) {
		delegatingFactoryBean.setKeypass(keypass);
	}

	protected final AsymmetricKeyBundle getBundle() {
		Assert.notNull(bundle, "bundle is not initialized");
		return bundle;
	}

}
