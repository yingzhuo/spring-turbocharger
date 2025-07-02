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

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.github.yingzhuo.turbocharger.util.crypto.SignatureUtils;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

/**
 * 通用型JWT算法 <br>
 * 签名/验证算法由PKCS#8格式的PEM文件决定
 *
 * @author 应卓
 * @see PemContent
 * @since 3.5.3
 */
public class GenericAlgorithm extends AbstractAlgorithm {

	private static final Map<String, String> ALG_NAME_MAPPING = Map.of(
		"SHA256withRSA", "RS256",
		"SHA385withRSA", "RS384",
		"SHA512withRSA", "RS512",
		"SHA256withECDSA", "ES256",
		"SHA384withECDSA", "ES384",
		"SHA512withECDSA", "ES512"
	);

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
		var newName = ALG_NAME_MAPPING.get(sigAlgName);
		return newName != null ? newName : sigAlgName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected byte[] doSign(byte[] data) {
		return SignatureUtils.sign(data, sigAlgName, privateKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doVerify(byte[] data, byte[] signature) {
		if (!SignatureUtils.verify(data, signature, sigAlgName, publicKey)) {
			throw new SignatureVerificationException(this);
		}
	}

}
