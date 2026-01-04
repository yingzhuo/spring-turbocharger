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

import com.github.yingzhuo.turbocharger.util.time.LocalDateTimeUtils;
import lombok.Getter;
import org.springframework.core.style.ToStringCreator;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;

/**
 * 锁帧 <br>
 * 用于记录锁成功时的一些基本信息
 *
 * @author 应卓
 * @since 3.4.0
 */
public final class LockFrame implements Serializable {

	private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

	/**
	 * 锁成功时的时间戳
	 */
	@Getter
	private final long creationTimestamp;

	@Getter
	private final String lockKey;

	@Getter
	private final String lockField;

	@Getter
	private final long ttlInSeconds;

	@Getter
	private final long reentrantCount;

	@Getter
	private final long threadId;

	@Getter
	private final String threadName;

	@Nullable
	private Timer timer = null;

	// -----------------------------------------------------------------------------------------------------------------

	public LockFrame(long creationTimestamp, String lockKey, String lockField, long ttlInSeconds, long reentrantCount, long threadId, String threadName) {
		this.creationTimestamp = creationTimestamp;
		this.lockKey = lockKey;
		this.lockField = lockField;
		this.ttlInSeconds = ttlInSeconds;
		this.reentrantCount = reentrantCount;
		this.threadId = threadId;
		this.threadName = threadName;
	}

	Optional<Timer> getNullableTimer() {
		return Optional.ofNullable(timer);
	}

	void setTimer(Timer timer) {
		this.timer = timer;
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return new ToStringCreator(this)
			.append("creation", LocalDateTimeUtils.toLocalDateTime(creationTimestamp, null).format(DEFAULT_TIME_FORMATTER))
			.append("lock-key", lockKey)
			.append("lock-field", lockField)
			.append("ttl", ttlInSeconds)
			.append("reentrant-count", reentrantCount)
			.append("thread-id", threadId)
			.append("thread-name", threadName)
			.toString();
	}

}
