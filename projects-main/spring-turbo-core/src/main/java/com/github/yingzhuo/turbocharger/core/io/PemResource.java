/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.core.io;

import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @see PemResourceProtocolResolver
 * @since 3.5.4
 */
@SuppressWarnings("unchecked")
public class PemResource extends DelegatingResource {

	private final @NonNull PemContent pemContent;
	private final @Nullable String keypass;

	/**
	 * 构造方法
	 *
	 * @param delegatingResource 代理的资源
	 */
	public PemResource(Resource delegatingResource) {
		this(delegatingResource, null);
	}

	/**
	 * 构造方法
	 *
	 * @param delegatingResource 代理的资源
	 * @param keypass            私钥密码
	 */
	public PemResource(Resource delegatingResource, @Nullable String keypass) {
		super(delegatingResource);
		this.keypass = keypass;

		try {
			this.pemContent = PemContent.of(delegatingResource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Nullable
	public String getKeypass() {
		return keypass;
	}

	public PemContent getPemContent() {
		return pemContent;
	}

	public List<X509Certificate> getCertificates() {
		return pemContent.getCertificates();
	}

	public X509Certificate getCertificate() {
		var certs = pemContent.getCertificates();
		if (certs.size() != 1) {
			throw new IllegalArgumentException("not unique certificate");
		}
		return certs.get(0);
	}

	public <T extends PublicKey> T getPublicKey() {
		return (T) getCertificate().getPublicKey();
	}

	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pemContent.getPrivateKey(keypass);
	}

	public KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}
}
