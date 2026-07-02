package com.github.yingzhuo.turbocharger.security.token.resolver;

import org.springframework.http.HttpHeaders;

/**
 * HTTP Bearer 令牌解析器
 *
 * @author 应卓
 * @see HeaderTokenResolver
 * @see HttpHeaders#AUTHORIZATION
 * @since 1.0.5
 */
public class BearerTokenResolver extends HeaderTokenResolver {

	private static final String PREFIX = "Bearer ";

	/**
	 * 默认构造方法
	 */
	public BearerTokenResolver() {
		super(HttpHeaders.AUTHORIZATION, PREFIX);
	}

}
