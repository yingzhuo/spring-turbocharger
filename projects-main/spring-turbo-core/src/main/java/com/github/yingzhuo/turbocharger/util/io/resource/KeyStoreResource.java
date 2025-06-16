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
package com.github.yingzhuo.turbocharger.util.io.resource;

import com.github.yingzhuo.turbocharger.util.keystore.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.keystore.KeyStoreUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.List;

/**
 * @author 应卓
 * @see KeyStore
 * @see KeyStoreType
 * @see KeyStoreUtils
 * @since 3.5.0
 */
public class KeyStoreResource extends AbstractNullStreamResource {

	private final KeyStoreType type;
	private final KeyStore store;
	private final String storepass;

	public KeyStoreResource(InputStream in, KeyStoreType type, String storepass) {
		Assert.notNull(in, "inputStream must not be null");
		Assert.notNull(type, "type must not be null");
		Assert.notNull(storepass, "storepass must not be null");

		this.type = type;
		this.storepass = storepass;
		this.store = KeyStoreUtils.loadKeyStore(in, type, storepass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return "KeyStore Resource [%s]".formatted(type.getValue().toUpperCase());
	}

	public String getStorepass() {
		return this.storepass;
	}

	public KeyStoreType getType() {
		return this.type;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public final List<Certificate> getCertificateChain(String alias) {
		return KeyStoreUtils.getCertificateChain(store, alias);
	}

	public final <T extends Certificate> T getCertificate(String alias) {
		return KeyStoreUtils.getCertificate(store, alias);
	}

	public final <T extends PublicKey> T getPublicKey(String alias) {
		return KeyStoreUtils.getPublicKey(store, alias);
	}

	public final <T extends PrivateKey> T getPrivateKey(String alias) {
		return getPrivateKey(alias, null);
	}

	public final <T extends PrivateKey> T getPrivateKey(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getPrivateKey(store, alias, keypass == null ? storepass : keypass);
	}

	public final <T extends SecretKey> T getSecretKey(String alias) {
		return getSecretKey(alias, null);
	}

	public final <T extends SecretKey> T getSecretKey(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getSecretKey(store, alias, keypass == null ? storepass : keypass);
	}

	public final KeyPair getKeyPair(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getKeyPair(store, alias, keypass == null ? storepass : keypass);
	}

}
