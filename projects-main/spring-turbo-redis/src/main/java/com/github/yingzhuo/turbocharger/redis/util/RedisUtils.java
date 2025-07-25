/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.redis.util;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * Redis相关工具
 *
 * @author 应卓
 * @since 3.4.0
 */
public final class RedisUtils {

	// 参考资料: https://cloud.tencent.com/developer/article/2410509

	private static final int DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP = 100;

	/**
	 * 私有构造方法
	 */
	private RedisUtils() {
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 按照匹配模式删除 string 类型的数据
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param pattern         要匹配的模式
	 * @param <V>             Value的泛型
	 */
	public static <V> void deleteValuesByPattern(RedisOperations<String, V> redisOperations, String pattern) {
		deleteValuesByPattern(redisOperations, pattern, DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP);
	}

	/**
	 * 按照匹配模式删除 string 类型的数据
	 *
	 * @param redisOperations           RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param pattern                   要匹配的模式
	 * @param deleteElementCountPerStep 每次删除的元素个数
	 * @param <V>                       Value的泛型
	 */
	public static <V> void deleteValuesByPattern(RedisOperations<String, V> redisOperations, String pattern, int deleteElementCountPerStep) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(pattern, "pattern is null or blank");
		Assert.isTrue(deleteElementCountPerStep >= 10, "deleteElementCountPerStep should >= 10");

		// @formatter:off
        var scanOptions = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .type(DataType.STRING)
                .build();
        // @formatter:on

		try (var c = redisOperations.scan(scanOptions)) {
			while (c.hasNext()) {
				var key = c.next();
				redisOperations.delete(key);
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 删除HASH类型BigKey
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             要删除的键
	 * @param <V>             Value的泛型
	 */
	public static <V> void deleteBigHash(RedisOperations<String, V> redisOperations, String key) {
		deleteBigHash(redisOperations, key, DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP);
	}

	/**
	 * 删除HASH类型BigKey
	 *
	 * @param redisOperations           RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key                       要删除的键
	 * @param deleteElementCountPerStep 每次删除的元素个数
	 * @param <V>                       Value的泛型
	 */
	public static <V> void deleteBigHash(RedisOperations<String, V> redisOperations, String key, int deleteElementCountPerStep) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(key, "key is null or blank");
		Assert.isTrue(deleteElementCountPerStep >= 10, "cursorCount should >= 10");

		var hashOp = redisOperations.opsForHash();

		// @formatter:off
        var scanOptions = ScanOptions.scanOptions()
                .count(deleteElementCountPerStep)
                .match("*")
                .build();
        // @formatter:on

		try (var c = hashOp.scan(key, scanOptions)) {
			while (c.hasNext()) {
				var entryKey = c.next().getKey();
				hashOp.delete(key, entryKey);
			}
		}

		redisOperations.delete(key);
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 删除LIST类型的BigKey
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             要删除的键
	 * @param <V>             Value的泛型
	 */
	public static <V> void deleteBigList(RedisOperations<String, V> redisOperations, String key) {
		deleteBigList(redisOperations, key, DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP);
	}

	/**
	 * 删除LIST类型的BigKey
	 *
	 * @param redisOperations           RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key                       要删除的键
	 * @param deleteElementCountPerStep 每次删除的元素个数
	 * @param <V>                       Value的泛型
	 */
	public static <V> void deleteBigList(RedisOperations<String, V> redisOperations, String key, int deleteElementCountPerStep) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.notNull(key, "key is null or blank");
		Assert.isTrue(deleteElementCountPerStep >= 10, "deleteElementCountPerStep should >= 10");

		var listOp = redisOperations.opsForList();
		var size = listOp.size(key);
		var count = 0L;

		if (size == null || size.equals(0L)) {
			return;
		}

		while (count < size) {
			listOp.trim(key, 0, deleteElementCountPerStep);
			count += deleteElementCountPerStep;
		}

		redisOperations.delete(key);
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 删除SET类型的BigKey
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             要删除的键
	 * @param <V>             Value的泛型
	 */
	public static <V> void deleteBigSet(RedisOperations<String, V> redisOperations, String key) {
		deleteBigSet(redisOperations, key, DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP);
	}

	/**
	 * 删除SET类型的BigKey
	 *
	 * @param redisOperations           RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key                       要删除的键
	 * @param deleteElementCountPerStep 每次删除的元素个数
	 * @param <V>                       Value的泛型
	 */
	public static <V> void deleteBigSet(RedisOperations<String, V> redisOperations, String key, int deleteElementCountPerStep) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(key, "key is null or blank");
		Assert.isTrue(deleteElementCountPerStep >= 10, "cursorCount should >= 10");

		var setOp = redisOperations.opsForSet();

		// @formatter:off
        var scanOptions = ScanOptions.scanOptions()
                .count(deleteElementCountPerStep)
                .match("*")
                .build();
        // @formatter:on

		try (var c = setOp.scan(key, scanOptions)) {
			while (c.hasNext()) {
				var item = c.next();
				setOp.remove(key, item);
			}
		}

		redisOperations.delete(key);
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 删除ZSET类型的BigKey
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             要删除的键
	 * @param <V>             Value的泛型
	 */
	public static <V> void deleteBigZset(RedisOperations<String, V> redisOperations, String key) {
		deleteBigSet(redisOperations, key, DEFAULT_DELETE_ELEMENT_COUNT_PER_STEP);
	}

	/**
	 * 删除ZSET类型的BigKey
	 *
	 * @param redisOperations           RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key                       要删除的键
	 * @param deleteElementCountPerStep 每次删除的元素个数
	 * @param <V>                       Value的泛型
	 */
	public static <V> void deleteBigZset(RedisOperations<String, V> redisOperations, String key, int deleteElementCountPerStep) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(key, "key is null or blank");
		Assert.isTrue(deleteElementCountPerStep >= 10, "cursorCount should >= 10");

		var zsetOp = redisOperations.opsForZSet();

		// @formatter:off
        var scanOptions = ScanOptions.scanOptions()
                .count(deleteElementCountPerStep)
                .match("*")
                .build();
        // @formatter:on

		try (var c = zsetOp.scan(key, scanOptions)) {
			while (c.hasNext()) {
				var item = c.next();
				zsetOp.remove(key, item.getValue());
			}
		}

		redisOperations.delete(key);
	}

}
