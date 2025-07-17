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

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Optional;

/**
 * 国密SM2签名算法
 *
 * @author 应卓
 * @since 3.5.3
 */
public class SM2Algorithm extends AbstractAlgorithm {

	private static final byte[] DEFAULT_ID = "国密-SM2".getBytes(StandardCharsets.UTF_8);

	private final SM2 sm2;
	private final byte[] id;

	/**
	 * 构造方法
	 *
	 * @param publicKey  公钥 (HEX或者Base64)
	 * @param privateKey 私钥 (HEX或者Base64)
	 */
	public SM2Algorithm(String publicKey, String privateKey) {
		this(publicKey, privateKey, null);
	}

	/**
	 * 构造方法
	 *
	 * @param publicKey  公钥 (HEX或者Base64)
	 * @param privateKey 私钥 (HEX或者Base64)
	 * @param id         加密salt
	 */
	public SM2Algorithm(String publicKey, String privateKey, @Nullable String id) {
		super("SM2");

		this.sm2 = SmUtil.sm2(privateKey, publicKey);
		this.sm2.setMode(SM2Engine.Mode.C1C3C2);

		this.id = Optional.ofNullable(id)
			.map(s -> s.getBytes(StandardCharsets.UTF_8))
			.orElse(DEFAULT_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doVerify(byte[] data, byte[] signature) {
		if (!sm2.verify(data, signature, id)) {
			throw new SignatureVerificationException(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected byte[] doSign(byte[] data) {
		return sm2.sign(data, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public X509Certificate getCertificate() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PublicKey getPublicKey() {
		return this.sm2.getPublicKey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrivateKey getPrivateKey() {
		return this.sm2.getPrivateKey();
	}

	public void setMode(SM2Engine.Mode mode) {
		sm2.setMode(mode);
	}

}
