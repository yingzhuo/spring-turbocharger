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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * 通用型JWT算法 <br>
 * 签名/验证算法由PKCS#8格式的PEM文件决定
 *
 * @author 应卓
 * @see com.auth0.jwt.algorithms.Algorithm
 * @see PemContent
 * @see GenericAlgorithmFactoryBean
 * @since 3.5.3
 */
public class GenericAlgorithm extends AbstractAlgorithm {

	private static final Map<String, String> ALG_NAME_MAPPING;

	static {
		ALG_NAME_MAPPING = Map.of(
			"SHA256withRSA", "RS256",
			"SHA385withRSA", "RS384",
			"SHA512withRSA", "RS512",
			"SHA256withECDSA", "ES256",
			"SHA384withECDSA", "ES384",
			"SHA512withECDSA", "ES512"
		);
	}

	private final X509Certificate certificate;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;
	private final String sigAlgName;

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
		super("<no name>", "<no description>");
		var pc = PemContent.of(pemContent);
		this.certificate = pc.getCertificates().get(0);
		this.sigAlgName = this.certificate.getSigAlgName();
		this.publicKey = this.certificate.getPublicKey();
		this.privateKey = pc.getPrivateKey(password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		var newName = ALG_NAME_MAPPING.get(sigAlgName);
		return newName != null ? newName : sigAlgName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected byte[] doSign(byte[] data) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initSign(privateKey, new SecureRandom());
		s.update(data);
		return s.sign();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doVerify(byte[] data, byte[] signature) throws Exception {
		var s = Signature.getInstance(sigAlgName);
		s.initVerify(publicKey);
		s.update(data);
		if (!s.verify(signature)) {
			throw new SignatureVerificationException(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public X509Certificate getCertificate() {
		return this.certificate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

}
