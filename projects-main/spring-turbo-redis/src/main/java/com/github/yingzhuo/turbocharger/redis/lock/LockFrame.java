package com.github.yingzhuo.turbocharger.redis.lock;

import com.github.yingzhuo.turbocharger.util.time.LocalDateTimeUtils;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;

public final class LockFrame implements Serializable {

	private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

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
