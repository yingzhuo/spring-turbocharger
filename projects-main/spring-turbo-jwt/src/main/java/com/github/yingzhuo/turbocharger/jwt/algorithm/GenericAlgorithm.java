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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.github.yingzhuo.turbocharger.util.crypto.SignatureUtils;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 通用型JWT算法
 *
 * @author 应卓
 * @see PemContent
 * @since 3.5.3
 */
public class GenericAlgorithm extends AbstractAlgorithm {

	private final String sigAlgName;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;

	/**
	 * 构造方法
	 *
	 * @param pemContent pem文件内容
	 */
	public GenericAlgorithm(String pemContent) {
		this(pemContent, null);
	}

	/**
	 * 构造方法
	 *
	 * @param pemContent pem文件内容
	 * @param password   秘钥密码
	 */
	public GenericAlgorithm(String pemContent, @Nullable String password) {
		super("<no name>");
		var pc = PemContent.of(pemContent);
		var cert = pc.getCertificates().get(0);
		this.sigAlgName = cert.getSigAlgName();
		this.publicKey = cert.getPublicKey();
		this.privateKey = pc.getPrivateKey(password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.sigAlgName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected byte[] doSign(byte[] data) throws SignatureGenerationException {
		return SignatureUtils.sign(data, sigAlgName, privateKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doVerify(byte[] data, byte[] signature) throws SignatureVerificationException {
		if (!SignatureUtils.verify(data, signature, sigAlgName, publicKey)) {
			throw new SignatureVerificationException(this);
		}
	}

}
