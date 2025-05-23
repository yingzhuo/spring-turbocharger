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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author 应卓
 * @since 3.5.0
 */
public class JwtServiceImpl implements JwtService {

	private static final VerificationCustomizer NOOP_CUSTOMIZER = verification -> {
	};

	private final Algorithm algorithm;
	private final VerificationCustomizer verificationCustomizer;

	/**
	 * 构造方法
	 *
	 * @param algorithm 签名算法
	 */
	public JwtServiceImpl(Algorithm algorithm) {
		this(algorithm, null);
	}

	/**
	 * 构造方法
	 *
	 * @param algorithm              签名算法
	 * @param verificationCustomizer verification 客制化工具
	 */
	public JwtServiceImpl(Algorithm algorithm, @Nullable VerificationCustomizer verificationCustomizer) {
		Assert.notNull(algorithm, "algorithm must not be null");
		this.algorithm = algorithm;
		this.verificationCustomizer = Objects.requireNonNullElse(verificationCustomizer, NOOP_CUSTOMIZER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createToken(JwtData data) {
		return JWT.create()
			.withHeader(data.getHeaderMap())
			.withPayload(data.getPayloadMap())
			.sign(algorithm);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValidatingResult validateToken(String token) {
		try {
			var verification = JWT.require(algorithm);
			verificationCustomizer.customize(verification);
			verification.build().verify(token);
		} catch (IncorrectClaimException | MissingClaimException ex) {
			return ValidatingResult.INVALID_CLAIM;
		} catch (TokenExpiredException ex) {
			return ValidatingResult.INVALID_TIME;
		} catch (SignatureVerificationException ex) {
			return ValidatingResult.INVALID_SIGNATURE;
		} catch (JWTVerificationException exception) {
			return ValidatingResult.INVALID_JWT_FORMAT;
		}
		return ValidatingResult.OK;
	}

}
