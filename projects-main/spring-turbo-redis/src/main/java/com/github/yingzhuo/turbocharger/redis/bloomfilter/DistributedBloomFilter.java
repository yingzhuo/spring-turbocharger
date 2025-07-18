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
package com.github.yingzhuo.turbocharger.redis.bloomfilter;

import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import com.github.yingzhuo.turbocharger.util.hash.BloomFilter;
import com.github.yingzhuo.turbocharger.util.hash.DigestHashFunction;
import com.github.yingzhuo.turbocharger.util.hash.HashFunction;
import lombok.Getter;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分布式布隆过滤器
 *
 * @author 应卓
 * @since 3.4.0
 */
public class DistributedBloomFilter implements BloomFilter {

	private static final int DEFAULT_BITMAP_SIZE = 10_0000_0000; // 十亿

	private final List<HashFunction> hashFunctions = new ArrayList<>(5);
	private final RedisOperations<String, String> redisOperations;
	private final String redisKey;

	@Getter
	private final int bitmapSize;

	/**
	 * 构造方法
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param redisKey        redis的键
	 */
	public DistributedBloomFilter(RedisOperations<String, String> redisOperations, String redisKey) {
		this(redisOperations, redisKey, DEFAULT_BITMAP_SIZE);
	}

	/**
	 * 构造方法
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param redisKey        redis的键
	 * @param bitmapSize      底层bitmap长度
	 */
	public DistributedBloomFilter(RedisOperations<String, String> redisOperations, String redisKey, int bitmapSize) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(redisKey, "redisKey is null or empty");
		Assert.isTrue(bitmapSize >= 1000_0000, "bitmapSize should >= 10000000");

		this.redisOperations = redisOperations;
		this.redisKey = redisKey;
		this.bitmapSize = bitmapSize;
	}

	/**
	 * 创建默认配置的布隆过滤器 <br>
	 * <ul>
	 *     <li>长度: 10_0000_0000</li>
	 *     <li>哈希函数1: MD5</li>
	 *     <li>哈希函数2: SHA-1</li>
	 *     <li>哈希函数3: SHA-256</li>
	 *     <li>哈希函数4: SHA-384</li>
	 *     <li>哈希函数5: SHA-512</li>
	 * </ul>
	 *
	 * @param redisOperations RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param key             Redis键
	 * @return 布隆过滤器实例
	 * @see HashFunction
	 * @see DigestHashFunction
	 */
	public static DistributedBloomFilter createDefault(
		RedisOperations<String, String> redisOperations,
		String key) {
		return new DistributedBloomFilter(redisOperations, key, DEFAULT_BITMAP_SIZE)
			.addHashFunctions(
				DigestHashFunction.md5(),
				DigestHashFunction.sha1(),
				DigestHashFunction.sha256(),
				DigestHashFunction.sha384(),
				DigestHashFunction.sha512()
			);
	}

	/**
	 * 获取已注册的哈希函数器
	 *
	 * @return 已注册的哈希函数器
	 */
	public List<HashFunction> getHashFunctions() {
		return Collections.unmodifiableList(hashFunctions);
	}

	/**
	 * 添加一个或多个哈希函数器
	 *
	 * @param first         第一个哈希函数器
	 * @param moreFunctions 多个其他哈希函数器
	 * @return this
	 */
	public DistributedBloomFilter addHashFunctions(HashFunction first, HashFunction... moreFunctions) {
		CollectionUtils.nullSafeAdd(hashFunctions, first);
		CollectionUtils.nullSafeAddAll(hashFunctions, moreFunctions);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(String element) {
		Assert.notNull(element, "element is null");
		Assert.notEmpty(hashFunctions, "hashFunctions is empty");

		hashFunctions.forEach(func -> {
			var offset = func.apply(element) % bitmapSize;
			offset = Math.abs(offset);
			redisOperations.opsForValue()
				.setBit(redisKey, offset, true);
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean mightContain(@Nullable String element) {
		// null认为不存在
		if (element == null) {
			return false;
		}

		Assert.notEmpty(hashFunctions, "hashFunctions is empty");

		for (var func : hashFunctions) {
			var offset = func.apply(element) % bitmapSize;
			offset = Math.abs(offset);
			var b = redisOperations.opsForValue()
				.getBit(redisKey, offset);

			if (!b) {
				return false;
			}
		}

		return true;
	}

}
