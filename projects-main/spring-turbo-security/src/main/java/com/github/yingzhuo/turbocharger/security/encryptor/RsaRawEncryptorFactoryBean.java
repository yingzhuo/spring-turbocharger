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
package com.github.yingzhuo.turbocharger.security.encryptor;

import com.github.yingzhuo.turbocharger.keystore.KeyBundle;
import com.github.yingzhuo.turbocharger.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.keystore.StoreKeyBundleFactoryBean;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.encrypt.RsaAlgorithm;
import org.springframework.security.crypto.encrypt.RsaRawEncryptor;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 3.5.0
 */
public class RsaRawEncryptorFactoryBean implements FactoryBean<RsaRawEncryptor>, InitializingBean {

	private final StoreKeyBundleFactoryBean delegatingFactoryBean = new StoreKeyBundleFactoryBean();

	@Setter
	private RsaAlgorithm rsaAlgorithm = RsaAlgorithm.DEFAULT;

	@Nullable
	private KeyBundle keyBundle;

	/**
	 * 默认构造方法
	 */
	public RsaRawEncryptorFactoryBean() {
		delegatingFactoryBean.setFormat(KeyStoreFormat.PKCS12);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RsaRawEncryptor getObject() {
		Assert.notNull(keyBundle, "RSA key bundle is required");
		return new RsaRawEncryptor(keyBundle.getKeyPair(), rsaAlgorithm);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return RsaRawEncryptor.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		delegatingFactoryBean.afterPropertiesSet();
		keyBundle = delegatingFactoryBean.getObject();
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

}
