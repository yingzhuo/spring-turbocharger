/*
 * Copyright 2025-present the original author or authors.
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

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * 抽象的JWT签名算法基类
 *
 * @author 应卓
 * @since 3.5.3
 */
public abstract class AbstractAlgorithm extends Algorithm {

	/**
	 * 构造方法
	 *
	 * @param name 算法名称
	 */
	protected AbstractAlgorithm(String name) {
		this(name, name);
	}

	/**
	 * 构造方法
	 *
	 * @param name        算法名称
	 * @param description 算法描述
	 */
	protected AbstractAlgorithm(String name, String description) {
		super(name, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void verify(DecodedJWT jwt) throws SignatureVerificationException {

		// header + '.' + payload
		var data = String.join(".", jwt.getHeader(), jwt.getPayload())
			.getBytes(StandardCharsets.UTF_8);

		// 签名
		var signature = Base64.getUrlDecoder().decode(jwt.getSignature());

		try {
			doVerify(data, signature);
		} catch (SignatureVerificationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureVerificationException(this, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final byte[] sign(byte[] bytes) throws SignatureGenerationException {
		try {
			return doSign(bytes);
		} catch (SignatureGenerationException e) {
			throw e;
		} catch (Exception e) {
			throw new SignatureGenerationException(this, e);
		}
	}

	/**
	 * 签名
	 *
	 * @param data 需要签名的数据
	 * @return 签名后的数据
	 */
	protected abstract byte[] doSign(byte[] data) throws Exception;

	/**
	 * 验证
	 *
	 * @param data      需要验证的部分
	 * @param signature 签名部分
	 */
	protected abstract void doVerify(byte[] data, byte[] signature) throws Exception;

	/**
	 * 获取证书
	 *
	 * @return 证书
	 */
	public abstract X509Certificate getCertificate();

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	public abstract PublicKey getPublicKey();

	/**
	 * 获取私钥
	 *
	 * @return 私钥
	 */
	public abstract PrivateKey getPrivateKey();

	/**
	 * 获取密钥对
	 *
	 * @return 密钥对
	 */
	public final KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

}
