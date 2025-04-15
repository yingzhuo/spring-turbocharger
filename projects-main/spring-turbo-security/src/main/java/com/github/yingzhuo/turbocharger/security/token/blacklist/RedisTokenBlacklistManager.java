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
package com.github.yingzhuo.turbocharger.security.token.blacklist;

import com.github.yingzhuo.turbocharger.security.exception.BlacklistTokenException;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringFormatter;
import com.github.yingzhuo.turbocharger.util.StringPool;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * @author 应卓
 * @since 2.0.5
 */
public class RedisTokenBlacklistManager implements TokenBlacklistManager {

	private final StringRedisTemplate redisTemplate;

	@Nullable
	private final Duration ttl;

	private String keyPrefix = StringPool.EMPTY;
	private String keySuffix = StringPool.EMPTY;

	public RedisTokenBlacklistManager(StringRedisTemplate redisTemplate, @Nullable Duration ttl) {
		this.redisTemplate = redisTemplate;
		this.ttl = ttl;
	}

	@Override
	public void save(Token token) {
		final var key = getKey(token);
		final var value = getValue(token);
		if (ttl == null) {
			redisTemplate.opsForValue().set(key, value);
		} else {
			redisTemplate.opsForValue().set(key, value, this.ttl);
		}
	}

	@Override
	public void verify(Token token) throws BlacklistTokenException {
		final var key = getKey(token);
		if (redisTemplate.opsForValue().get(key) != null) {
			var msg = StringFormatter.format("token \"{}\" is blacklisted", token.asString());
			throw new BlacklistTokenException(msg);
		}
	}

	protected String getKey(Token token) {
		return keyPrefix + token.asString() + keySuffix;
	}

	protected String getValue(Token token) {
		return token.asString();
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public void setKeySuffix(String keySuffix) {
		this.keySuffix = keySuffix;
	}

}
