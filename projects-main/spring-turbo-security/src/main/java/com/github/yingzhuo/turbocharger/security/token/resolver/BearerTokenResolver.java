package com.github.yingzhuo.turbocharger.security.token.resolver;

import org.springframework.http.HttpHeaders;

public class BearerTokenResolver extends HeaderTokenResolver {

	private static final String PREFIX = "Bearer ";

	public BearerTokenResolver() {
		super(HttpHeaders.AUTHORIZATION, PREFIX);
	}

}
