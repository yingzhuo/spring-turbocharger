package com.github.yingzhuo.turbocharger.security.jwt;

import com.github.yingzhuo.turbocharger.jwt.JwtValidator;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtAlgorithmTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtClaimTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtFormatTokenException;
import com.github.yingzhuo.turbocharger.security.jwt.exception.BadJwtTimeTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractJwtTokenToUserConverter implements TokenToUserConverter {

	private final JwtValidator jwtValidator;

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
