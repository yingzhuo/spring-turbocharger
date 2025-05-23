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
package com.github.yingzhuo.turbocharger.util.crypto.bundle;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.security.KeyPair;

import static com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreHelper.*;

/**
 * @author 应卓
 * @since 3.3.1
 */
public class KeyStoreAsymmetricKeyBundleFactoryBean implements FactoryBean<AsymmetricKeyBundle>, InitializingBean {

	@Setter
	private String location;

	@Setter
	private KeyStoreFormat format = KeyStoreFormat.PKCS12;

	@Setter
	private String storepass;

	@Setter
	private String alias;

	@Setter
	private String keypass;

	private AsymmetricKeyBundle bundle;

	/**
	 * {@inheritDoc}
	 */
	@NonNull
	@Override
	public AsymmetricKeyBundle getObject() {
		return this.bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return AsymmetricKeyBundle.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(location, "location is required");
		Assert.notNull(format, "format is required");
		Assert.notNull(storepass, "storepass is required");
		Assert.notNull(alias, "alias is required");
		Assert.notNull(keypass, "keypass is required");

		var keyStoreResource = ResourceUtils.loadResource(this.location);

		try (var inputStream = keyStoreResource.getInputStream()) {
			var store = loadKeyStore(inputStream, format, storepass);
			var cert = getCertificate(store, alias);
			var privateKey = getPrivateKey(store, alias, keypass);
			this.bundle = new AsymmetricKeyBundleImpl(new KeyPair(cert.getPublicKey(), privateKey), cert);
		}
	}

}
