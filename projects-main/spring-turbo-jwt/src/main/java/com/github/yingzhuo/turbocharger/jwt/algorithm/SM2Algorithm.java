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

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.ByteBuffer;
import java.util.Base64;

/**
 * @author 应卓
 * @since 3.5.3
 */
public class SM2Algorithm extends Algorithm {

	private final SM2 sm2;

	public SM2Algorithm(String publicKey, String privateKey) {
		super("SM2", "SM2");
		this.sm2 = SmUtil.sm2(privateKey, publicKey);
	}

	@Override
	public void verify(DecodedJWT jwt) throws SignatureVerificationException {
		byte[] signatureBytes = Base64.getUrlDecoder().decode(jwt.getSignature());

		byte[] headerBytes = Base64.getUrlDecoder().decode(jwt.getHeader());
		byte[] payloadBytes = Base64.getUrlDecoder().decode(jwt.getPayload());
		byte[] combinedData = new byte[headerBytes.length + payloadBytes.length];

		ByteBuffer buffer = ByteBuffer.wrap(combinedData);
		buffer.put(headerBytes);
		buffer.put(payloadBytes);
		combinedData = buffer.array();

		var ok = sm2.verify(combinedData, signatureBytes);
		if (!ok) {
			throw new SignatureVerificationException(this);
		}
	}

	@Override
	public byte[] sign(byte[] bytes) throws SignatureGenerationException {
		return sm2.sign(bytes);
	}

}
