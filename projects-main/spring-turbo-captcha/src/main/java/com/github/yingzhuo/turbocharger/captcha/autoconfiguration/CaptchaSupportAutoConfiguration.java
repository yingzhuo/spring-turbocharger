package com.github.yingzhuo.turbocharger.captcha.autoconfiguration;

import com.github.yingzhuo.turbocharger.captcha.support.AccessKeyGenerator;
import com.github.yingzhuo.turbocharger.captcha.support.SimpleAccessKeyGenerator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnMissingBean(AccessKeyGenerator.class)
public class CaptchaSupportAutoConfiguration {

	@Bean
	public AccessKeyGenerator captchaAccessKeyGenerator() {
		return new SimpleAccessKeyGenerator();
	}

}
