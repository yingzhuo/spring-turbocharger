package com.github.yingzhuo.turbocharger.jwt;

/**
 * @author 应卓
 * @since 3.5.3
 */
@FunctionalInterface
public interface JwtValidator {

	/**
	 * 验证令牌是否合法
	 *
	 * @param token 令牌
	 * @return 验证结果
	 * @see ValidatingResult
	 */
	public ValidatingResult validateToken(String token);

}
