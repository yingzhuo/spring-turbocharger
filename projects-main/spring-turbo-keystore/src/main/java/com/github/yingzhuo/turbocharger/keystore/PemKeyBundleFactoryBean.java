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

import com.github.yingzhuo.turbocharger.keystore.util.PemUtils;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.KeyPair;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.readResourceAsString;
import static com.github.yingzhuo.turbocharger.keystore.util.PemUtils.readPkcs8PrivateKey;
import static com.github.yingzhuo.turbocharger.keystore.util.PemUtils.readX509Certificate;

/**
 * {@link KeyBundle} 配置用 {@link FactoryBean} <br>
 * 用PEM格式文件配置
 *
 * @see KeyBundle
 * @see PemUtils
 * @see StoreKeyBundleFactoryBean
 * @since 应卓
 * @since 3.3.1
 */
public class PemKeyBundleFactoryBean implements FactoryBean<KeyBundle>, InitializingBean {

	@Setter
	private String certificateLocation;

	@Setter
	private String certificateContent = "";

	@Setter
	private String privateKeyLocation;

	@Setter
	private String privateKeyContent = "";

	@Setter
	private String privateKeyPassword;

	@Nullable
	private KeyBundle bundle;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KeyBundle getObject() {
		Assert.notNull(bundle, "bundle is not initialized");
		return bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return KeyBundle.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		var cert = readX509Certificate(getCertContent());
		var privateKey = readPkcs8PrivateKey(getPrivateKeyContent(), privateKeyPassword);
		bundle = new KeyBundleImpl(new KeyPair(cert.getPublicKey(), privateKey), cert);
	}

	private String getCertContent() {
		if (StringUtils.hasText(this.certificateContent)) {
			return this.certificateContent;
		}

		Assert.notNull(this.certificateLocation, "certificateLocation is required");
		return readResourceAsString(certificateLocation);
	}

	private String getPrivateKeyContent() {
		if (StringUtils.hasText(this.privateKeyContent)) {
			return this.privateKeyContent;
		}

		Assert.notNull(this.privateKeyLocation, "keyLocation is required");
		return readResourceAsString(privateKeyLocation);
	}

}
