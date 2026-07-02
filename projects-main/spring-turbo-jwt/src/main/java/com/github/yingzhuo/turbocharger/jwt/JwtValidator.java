package com.github.yingzhuo.turbocharger.jwt;

@FunctionalInterface
public interface JwtValidator {

	public ValidatingResult validateToken(String token);

}
