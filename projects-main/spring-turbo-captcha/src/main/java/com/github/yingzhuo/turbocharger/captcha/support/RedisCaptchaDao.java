/*
 *
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
 *
 */
package com.github.yingzhuo.turbocharger.captcha.support;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * {@link CaptchaDao}的Redis相关实现
 *
 * @author 应卓
 * @since 1.0.1
 */
public class RedisCaptchaDao implements CaptchaDao {

	private final StringRedisTemplate redisTemplate;

	public RedisCaptchaDao(StringRedisTemplate template) {
		this.redisTemplate = template;
	}

	@Override
	public void save(String accessKey, String captchaWord, @Nullable Duration ttl) {
		if (ttl == null) {
			redisTemplate.opsForValue().set(accessKey, captchaWord);
		} else {
			redisTemplate.opsForValue().set(accessKey, captchaWord, ttl);
		}
	}

	@Nullable
	@Override
	public String find(String accessKey) {
		return redisTemplate.opsForValue().get(accessKey);
	}

	@Override
	public void delete(String accessKey) {
		redisTemplate.delete(accessKey);
	}

}
