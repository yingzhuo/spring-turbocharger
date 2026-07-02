package com.github.yingzhuo.turbocharger.redis.lock;

import com.github.yingzhuo.turbocharger.util.concurrent.CurrentThreadUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;

public final class DistributedReentrantLock implements Serializable {

	/*
	 * 本工具没有在高并发下严格测试，作者只为了自学与教学。请谨慎在生产环境上使用。
	 */

	private static final RedisScript<Long> TRY_LOCK =
		RedisScript.of(new ClassPathResource("META-INF/Lock#lock.lua"), Long.class);

	private static final RedisScript<Boolean> UNLOCK =
		RedisScript.of(new ClassPathResource("META-INF/Lock#unlock.lua"), Boolean.class);

	private final RedisOperations<String, String> redisOperations;
	private final LockStack lockStack;
	private final long ttlInSeconds;
	private final String lockKey;

	public DistributedReentrantLock(RedisOperations<String, String> redisOperations, String lockKey, long ttlInSeconds) {
		Assert.notNull(redisOperations, "redisOperations is required");
		Assert.hasText(lockKey, "lockKey is required");
		Assert.isTrue(ttlInSeconds > 0, "ttlInSeconds must greater than 0");

		this.redisOperations = redisOperations;
		this.lockKey = lockKey;
		this.ttlInSeconds = ttlInSeconds;
		this.lockStack = new LockStack();
	}

	public boolean tryLock() {
		var now = System.currentTimeMillis();
		var lockField = CurrentThreadUtils.getTrait();

		long reentrantCount = redisOperations.execute(
			TRY_LOCK,
			List.of(),
			lockKey,
			lockField,
			String.valueOf(ttlInSeconds)
		);

		if (reentrantCount >= 1) {
			// 栈桢入栈并返回
			var frame = new LockFrame(
				now,
				lockKey, lockField, ttlInSeconds,
				reentrantCount,
				CurrentThreadUtils.getId(), CurrentThreadUtils.getName()
			);
			lockStack.push(frame);
			return true;
		} else {
			return false;
		}
	}

	public boolean unlock() {
		var lockField = CurrentThreadUtils.getTrait();

		var success = redisOperations.execute(
			UNLOCK,
			List.of(),
			lockKey,
			lockField
		);

		if (success) {
			var frame = lockStack.peek();

			if (frame != null && frame.getLockField().equals(lockField)) {
				frame.getNullableTimer().ifPresent(Timer::cancel);
				lockStack.pop();
			}
		}

		return success;
	}

	public void renewTtl() {
		var timer = new Timer(true);
		var lockField = CurrentThreadUtils.getTrait();
		timer.schedule(new RenewTask(redisOperations, lockKey, lockField, ttlInSeconds), ttlInSeconds * 1000 * 2 / 3);

		var frame = lockStack.peek();
		frame.setTimer(timer);
	}

	@Nullable
	public LockFrame getCurrentFrame() {
		return lockStack.peek();
	}

}
