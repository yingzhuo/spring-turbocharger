/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.redis.lock;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;
import java.util.TimerTask;

/**
 * 分布式锁刷新TTL任务
 *
 * @author 应卓
 * @see java.util.Timer
 * @see DistributedReentrantLock
 * @since 3.4.0
 */
final class RenewTask extends TimerTask {

	private static final RedisScript<Boolean> RENEW_TTL =
		RedisScript.of(new ClassPathResource("META-INF/Lock#renew-ttl.lua"), Boolean.class);

	private final RedisOperations<String, String> redisOperations;
	private final String key;
	private final String field;
	private final long ttl;

	/**
	 * 构造方法
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             key
	 * @param field           Hash field
	 * @param ttl             自动过期时间
	 */
	public RenewTask(RedisOperations<String, String> redisOperations, String key, String field, long ttl) {
		this.redisOperations = redisOperations;
		this.key = key;
		this.field = field;
		this.ttl = ttl;
	}

	@Override
	public void run() {
		redisOperations.execute(
			RENEW_TTL,
			List.of(),
			key,
			field,
			String.valueOf(ttl)
		);
	}

}
