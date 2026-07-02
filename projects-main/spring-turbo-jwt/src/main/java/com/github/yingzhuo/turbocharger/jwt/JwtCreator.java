package com.github.yingzhuo.turbocharger.jwt;

/**
 * @author 应卓
 * @since 3.5.3
 */
@FunctionalInterface
public interface JwtCreator {

	/**
	 * 生成JWT令牌
	 *
	 * @param data 令牌数据
	 * @return JWT令牌
	 */
	public String createToken(JwtData data);

}
