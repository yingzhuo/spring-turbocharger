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
package com.github.yingzhuo.turbocharger.jwt;

import com.github.yingzhuo.turbocharger.jwt.alg.JwtSigner;
import com.github.yingzhuo.turbocharger.jwt.alg.KeyPairJwtSigner;
import com.github.yingzhuo.turbocharger.jwt.alg.SecretKeyJwtSigner;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Optional;

/**
 * @author 应卓
 * @since 3.3.2
 */
public class JwtServiceImpl implements JwtService {

	private final JwtSigner signer;

	/**
	 * 构造方法
	 *
	 * @param signer 签名器
	 */
	public JwtServiceImpl(JwtSigner signer) {
		Assert.notNull(signer, "signer must not be null");
		this.signer = signer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createToken(JwtData data) {
		return Jwts.builder()
			.header()
			.add(data.getHeaderMap())
			.and()
			.claims(data.getPayloadMap())
			.signWith(getSignerKey())
			.compact();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValidatingResult validateToken(String token, @Nullable JwtAssertions jwtAssertions) {

		if (!StringUtils.hasText(token)) {
			return ValidatingResult.INVALID_JWT_FORMAT;
		}

		var builder = Jwts.parser();

		Optional.ofNullable(jwtAssertions)
			.ifPresent(assertions -> {
				if (!assertions.isEmpty()) {
					for (var key : assertions.keySet()) {
						var value = assertions.get(key);
						builder.require(key, value);
					}
				}
			});

		Optional.ofNullable(getVerifyPublicKey())
			.ifPresent(builder::verifyWith);

		Optional.ofNullable(getVerifySecretKey())
			.ifPresent(builder::verifyWith);

		try {
			builder.build()
				.parse(token);
		} catch (ExpiredJwtException | PrematureJwtException e) {
			return ValidatingResult.INVALID_TIME;
		} catch (SecurityException e) {
			return ValidatingResult.INVALID_SIGNATURE;
		} catch (MalformedJwtException | IllegalArgumentException e) {
			return ValidatingResult.INVALID_JWT_FORMAT;
		} catch (MissingClaimException | IncorrectClaimException e) {
			return ValidatingResult.INVALID_CLAIM;
		}

		return ValidatingResult.OK;
	}

	private Key getSignerKey() {
		if (signer instanceof KeyPairJwtSigner keyPairJwtSigner) {
			return keyPairJwtSigner.keyPair().getPrivate();     // 签名用私钥，验证用公钥
		}

		if (signer instanceof SecretKeyJwtSigner secretKeyJwtSigner) {
			return secretKeyJwtSigner.secretKey();
		}

		var msg = String.format("unsupported type: %s", signer.getClass().getName());
		throw new IllegalStateException(msg);
	}

	@Nullable
	private PublicKey getVerifyPublicKey() {
		if (signer instanceof KeyPairJwtSigner keyPairJwtSigner) {
			return keyPairJwtSigner.keyPair().getPublic();
		}
		return null;
	}

	@Nullable
	private SecretKey getVerifySecretKey() {
		if (signer instanceof SecretKeyJwtSigner secretKeyJwtSigner) {
			return secretKeyJwtSigner.secretKey();
		}
		return null;
	}

}
