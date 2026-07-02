package com.github.yingzhuo.turbocharger.redis.bloomfilter;

import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import com.github.yingzhuo.turbocharger.util.hash.BloomFilter;
import com.github.yingzhuo.turbocharger.util.hash.DigestHashFunction;
import com.github.yingzhuo.turbocharger.util.hash.HashFunction;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistributedBloomFilter implements BloomFilter {

	private static final int DEFAULT_BITMAP_SIZE = 10_0000_0000; // 十亿

	private final List<HashFunction> hashFunctions = new ArrayList<>(5);
	private final RedisOperations<String, String> redisOperations;
	private final String redisKey;

	@Getter
	private final int bitmapSize;

	public DistributedBloomFilter(RedisOperations<String, String> redisOperations, String redisKey) {
		this(redisOperations, redisKey, DEFAULT_BITMAP_SIZE);
	}

	public DistributedBloomFilter(RedisOperations<String, String> redisOperations, String redisKey, int bitmapSize) {
		Assert.notNull(redisOperations, "redisOperations is null");
		Assert.hasText(redisKey, "redisKey is null or empty");
		Assert.isTrue(bitmapSize >= 1000_0000, "bitmapSize should >= 10000000");

		this.redisOperations = redisOperations;
		this.redisKey = redisKey;
		this.bitmapSize = bitmapSize;
	}

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

	public List<HashFunction> getHashFunctions() {
		return Collections.unmodifiableList(hashFunctions);
	}

	public DistributedBloomFilter addHashFunctions(HashFunction first, HashFunction... moreFunctions) {
		CollectionUtils.nullSafeAdd(hashFunctions, first);
		CollectionUtils.nullSafeAddAll(hashFunctions, moreFunctions);
		return this;
	}

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
