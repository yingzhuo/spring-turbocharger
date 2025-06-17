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
 * 基于PEM文件的{@link org.springframework.core.io.Resource}
 *
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

	/**
	 * 构造方法
	 *
	 * @param content 文件内容
	 */
	public PemResource(String content) {
		this(content, null);
	}

	/**
	 * 构造方法
	 *
	 * @param content 文件内容
	 * @param keypass 私钥的密码
	 */
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

	/**
	 * 获取证书链
	 *
	 * @return 证书链
	 */
	public List<X509Certificate> getCertificateChain() {
		return pc.getCertificates();
	}

	/**
	 * 获取唯一一个证书
	 *
	 * @param <T> 证书的泛型类型
	 * @return 证书
	 */
	public <T extends X509Certificate> T getUniqueCertificate() {
		return (T) pc.getCertificates().get(0);
	}

	/**
	 * 获取公钥
	 *
	 * @param <T> 公钥泛型
	 * @return 公钥泛型类型
	 */
	public <T extends PublicKey> T getPublicKey() {
		return (T) getUniqueCertificate().getPublicKey();
	}

	/**
	 * 获取私钥
	 *
	 * @param <T> 私钥泛型
	 * @return 私钥
	 */
	public <T extends PrivateKey> T getPrivateKey() {
		return (T) pc.getPrivateKey(keypass);
	}

	/**
	 * 获取私钥密码
	 *
	 * @return 私钥密码
	 */
	@Nullable
	public String getKeypass() {
		return this.keypass;
	}

}
