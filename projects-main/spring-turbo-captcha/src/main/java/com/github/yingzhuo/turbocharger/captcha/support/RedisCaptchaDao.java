package com.github.yingzhuo.turbocharger.captcha.support;

import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

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
