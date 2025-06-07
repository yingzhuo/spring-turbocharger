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
package com.github.yingzhuo.turbocharger.util.keystore;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.0
 */
@SuppressWarnings("unchecked")
public interface KeyBundle extends Serializable {

	@Nullable
	public String getAlias();

	public List<X509Certificate> getCertificateChain();

	public default  <T extends X509Certificate> T getCertificate() {
		var lst = getCertificateChain();
		Assert.isTrue(lst.size() == 1, "certificate chain size must be 1");
		return (T) lst.get(0);
	}

	public <T extends Key> T getKey();

	public default <T extends SecretKey> T getSecretKey() {
		return (T) getKey();
	}

	public default <T extends PrivateKey> T getPrivateKey() {
		return (T) getKey();
	}

	public default <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	public default KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

}
