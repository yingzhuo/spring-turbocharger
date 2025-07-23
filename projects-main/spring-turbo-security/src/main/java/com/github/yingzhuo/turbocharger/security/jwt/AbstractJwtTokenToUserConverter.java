/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.jwt;

import com.github.yingzhuo.turbocharger.jwt.JwtValidator;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtAlgorithmTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtClaimTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtFormatTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtTimeTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 2.2.4
 */
public abstract class AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	private final JwtValidator jwtValidator;

	/**
	 * 构造方法
	 *
	 * @param jwtValidator JWT验证器
	 */
	protected AbstractJwtTokenToUserConverter(JwtValidator jwtValidator) {
		Assert.notNull(jwtValidator, "jwtValidator is required");
		this.jwtValidator = jwtValidator;
	}

	@Nullable
	@Override
	public final UserDetails convert(@Nullable Token token) throws AuthenticationException {
		if (token == null) {
			return null;
		}

		var rawToken = token.asString();

		var parts = rawToken.split("\\.");
		if (parts.length != 3) {
			throw new BadJwtFormatTokenException(StringFormatter.format("invalid toke: {}", rawToken));
		}

		var result = jwtValidator.validateToken(token.asString());

		switch (result) {
			case INVALID_JWT_FORMAT:
				throw new BadJwtFormatTokenException(StringFormatter.format("invalid toke: {}", rawToken));
			case INVALID_SIGNATURE:
				throw new BadJwtAlgorithmTokenException(StringFormatter.format("invalid signature: {}", rawToken));
			case INVALID_TIME:
				throw new BadJwtTimeTokenException(StringFormatter.format("invalid time: {}", rawToken));
			case INVALID_CLAIM:
				throw new BadJwtClaimTokenException(StringFormatter.format("invalid claim: {}", rawToken));
			case OK:
				break;
		}

		var decoder = Base64.getUrlDecoder();
		var headerJson = new String(decoder.decode(parts[0].getBytes(UTF_8)));
		var payloadJson = new String(decoder.decode(parts[1].getBytes(UTF_8)));

		return doAuthenticate(
			headerJson,
			payloadJson
		);
	}

	@Nullable
	protected abstract UserDetails doAuthenticate(
		String headerJson,
		String payloadJson) throws AuthenticationException;

}
