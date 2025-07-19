/*
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
 */
package com.github.yingzhuo.turbocharger.secret;

import org.springframework.lang.NonNull;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.3
 */
@SuppressWarnings("unchecked")
public class KeyBundleImpl implements KeyBundle {

	private final @NonNull X509Certificate certificate;
	private final @NonNull PrivateKey privateKey;

	/**
	 * 构造方法
	 *
	 * @param certificate 证书
	 * @param privateKey  私钥
	 */
	public KeyBundleImpl(@NonNull X509Certificate certificate,
						 @NonNull PrivateKey privateKey) {
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends PrivateKey> T getPrivateKey() {
		return (T) this.privateKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends X509Certificate> T getCertificate() {
		return (T) this.certificate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<X509Certificate> getCertificateChain() {
		return List.of(this.certificate);
	}

}
