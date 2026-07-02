package com.github.yingzhuo.turbocharger.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

/**
 * {@link JwtService} 默认实现
 *
 * @author 应卓
 * @since 3.5.0
 */
public class JwtServiceImpl implements JwtService {

	private final Algorithm algorithm;
	private final @Nullable VerificationCustomizer verificationCustomizer;

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
		this.verificationCustomizer = verificationCustomizer;
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
			if (verificationCustomizer != null) {
				verificationCustomizer.customize(verification);
			}
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
