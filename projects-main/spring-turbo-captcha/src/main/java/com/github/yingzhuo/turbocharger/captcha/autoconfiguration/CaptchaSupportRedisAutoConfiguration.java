/*
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.captcha.autoconfiguration;

import com.github.yingzhuo.turbocharger.captcha.support.CaptchaDao;
import com.github.yingzhuo.turbocharger.captcha.support.RedisCaptchaDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 应卓
 * @since 1.3.0
 */
@ConditionalOnMissingBean(CaptchaDao.class)
@ConditionalOnBean(type = "org.springframework.data.redis.core.StringRedisTemplate")
public class CaptchaSupportRedisAutoConfiguration {

	@Bean
	public CaptchaDao captchaDao(StringRedisTemplate template) {
		return new RedisCaptchaDao(template);
	}

}
