package com.github.yingzhuo.turbocharger.jwt;

@FunctionalInterface
public interface JwtCreator {

	public String createToken(JwtData data);

}
