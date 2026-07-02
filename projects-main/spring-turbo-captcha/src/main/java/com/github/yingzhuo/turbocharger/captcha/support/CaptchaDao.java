package com.github.yingzhuo.turbocharger.captcha.support;

import org.jspecify.annotations.Nullable;

import java.time.Duration;

public interface CaptchaDao {

	public default void save(String accessKey, String captchaWord) {
		save(accessKey, captchaWord, null);
	}

	public void save(String accessKey, String captchaWord, @Nullable Duration ttl);

	@Nullable
	public String find(String accessKey);

	public void delete(String accessKey);

}
