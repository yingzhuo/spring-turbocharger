package com.github.yingzhuo.turbocharger.captcha.autoconfiguration;

import com.github.yingzhuo.turbocharger.captcha.support.CaptchaDao;
import com.github.yingzhuo.turbocharger.captcha.support.RedisCaptchaDao;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 应卓
 * @since 1.3.0
 */
@AutoConfiguration
@ConditionalOnMissingBean(CaptchaDao.class)
@ConditionalOnBean(type = "org.springframework.data.redis.core.StringRedisTemplate")
public class CaptchaSupportRedisAutoConfiguration {

	@Bean
	public CaptchaDao captchaDao(StringRedisTemplate template) {
		return new RedisCaptchaDao(template);
	}

}
