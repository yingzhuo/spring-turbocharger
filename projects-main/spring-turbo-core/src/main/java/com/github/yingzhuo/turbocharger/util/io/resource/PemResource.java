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

import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.0
 */
@SuppressWarnings("unchecked")
public class PemResource extends TextResource {

	@Nullable
	private final String keypass;

	private final PemContent pc;

	public PemResource(String content, @Nullable String keypass) {
		super(content);
		this.keypass = keypass;
		this.pc = PemContent.of(content);
	}

	public List<X509Certificate> getCertificateList() {
		return pc.getCertificates();
	}

	public <T extends X509Certificate> T getFirstCertificate() {
		return (T) pc.getCertificates().get(0);
	}

	public <T extends PublicKey> T getPublicKey() {
		return (T) getFirstCertificate().getPublicKey();
	}

	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pc.getPrivateKey(keypass);
	}

	public String getKeypass() {
		return this.keypass;
	}

}
