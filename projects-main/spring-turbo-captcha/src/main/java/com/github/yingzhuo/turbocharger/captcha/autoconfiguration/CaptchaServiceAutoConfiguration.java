package com.github.yingzhuo.turbocharger.captcha.autoconfiguration;

import com.github.yingzhuo.turbocharger.captcha.CaptchaService;
import com.github.yingzhuo.turbocharger.captcha.google.GoogleCaptchaService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.3.0
 */
@AutoConfiguration
@ConditionalOnMissingBean(CaptchaService.class)
public class CaptchaServiceAutoConfiguration {

	@Bean
	public CaptchaService captchaService() {
		return new GoogleCaptchaService();
	}

}
