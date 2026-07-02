package com.github.yingzhuo.turbocharger.captcha.support;

/**
 * @author 应卓
 * @since 1.0.1
 */
@FunctionalInterface
public interface AccessKeyGenerator {

	public String generate();

}
