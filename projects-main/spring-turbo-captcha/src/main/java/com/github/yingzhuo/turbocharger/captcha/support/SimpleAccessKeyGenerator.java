package com.github.yingzhuo.turbocharger.captcha.support;

import com.github.yingzhuo.turbocharger.util.id.UUIDs;
import org.springframework.beans.factory.annotation.Value;

public class SimpleAccessKeyGenerator implements AccessKeyGenerator {

	@Value("${spring.application.name:unknown-application}")
	private String applicationName;

	@Override
	public String generate() {
		return applicationName + "-captcha-access-key-" + UUIDs.classic32();
	}

}
