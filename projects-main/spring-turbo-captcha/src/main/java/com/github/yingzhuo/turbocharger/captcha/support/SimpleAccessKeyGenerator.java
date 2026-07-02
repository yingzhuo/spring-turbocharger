package com.github.yingzhuo.turbocharger.captcha.support;

import com.github.yingzhuo.turbocharger.util.id.UUIDs;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link AccessKeyGenerator}的默认实现
 *
 * @author 应卓
 * @since 1.0.1
 */
public class SimpleAccessKeyGenerator implements AccessKeyGenerator {

	@Value("${spring.application.name:unknown-application}")
	private String applicationName;

	@Override
	public String generate() {
		return applicationName + "-captcha-access-key-" + UUIDs.classic32();
	}

}
