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
package com.github.yingzhuo.turbocharger.key;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.3
 */
public interface KeyBundle extends Serializable {

	public <T extends PublicKey> T getPublicKey();

	public <T extends PrivateKey> T getPrivateKey();

	public default KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

	public <T extends X509Certificate> T getCertificate();

	public List<X509Certificate> getCertificateChain();

	@Nullable
	public default String alias() {
		return null;
	}

}
