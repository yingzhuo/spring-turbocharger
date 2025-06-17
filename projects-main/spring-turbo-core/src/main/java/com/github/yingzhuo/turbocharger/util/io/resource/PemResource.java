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
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see PemResourceProtocolResolver
 * @see PemContent
 * @see X509Certificate
 * @see PublicKey
 * @see PrivateKey
 * @since 3.5.0
 */
@SuppressWarnings("unchecked")
public class PemResource extends AbstractTextResource {

	@NonNull
	private final PemContent pc;

	@Nullable
	private final String keypass;

	public PemResource(String content) {
		this(content, null);
	}

	public PemResource(String content, @Nullable String keypass) {
		super(
			Stream.of(content.split("\n"))
				.map(String::trim)
				.filter(StringUtils::hasText)
				.collect(Collectors.joining("\n"))
		);
		this.keypass = keypass;
		this.pc = PemContent.of(content);
	}

	public static PemResource of(CharSequence text) {
		return new PemResource(text.toString());
	}

	public List<X509Certificate> getCertificateChain() {
		return pc.getCertificates();
	}

	public <T extends X509Certificate> T getUniqueCertificate() {
		return (T) pc.getCertificates().get(0);
	}

	public <T extends PublicKey> T getPublicKey() {
		return (T) getUniqueCertificate().getPublicKey();
	}

	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pc.getPrivateKey(keypass);
	}

	@Nullable
	public String getKeypass() {
		return this.keypass;
	}

}
