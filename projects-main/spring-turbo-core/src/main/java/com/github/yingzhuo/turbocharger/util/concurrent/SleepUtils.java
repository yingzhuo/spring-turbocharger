package com.github.yingzhuo.turbocharger.util.concurrent;

import com.github.yingzhuo.turbocharger.util.RandomUtils;
import org.springframework.util.Assert;

import java.time.Duration;

public final class SleepUtils {

	private SleepUtils() {
		super();
	}

	public static void sleep(Duration duration) {
		sleep(duration, false);
	}

	public static void sleep(Duration duration, boolean resetInterruptFlagWhenInterrupted) {
		Assert.notNull(duration, "duration is required");

		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			if (resetInterruptFlagWhenInterrupted) {
				Thread.currentThread().interrupt();
			} else {
				throw new UncheckedInterruptedException(e);
			}
		}
	}

	public static void sleepRandomSeconds(long startInclusive, long endExclusive) {
		sleepRandomSeconds(startInclusive, endExclusive, false);
	}

	public static void sleepRandomSeconds(long startInclusive, long endExclusive, boolean resetInterruptFlagWhenInterrupted) {
		final var seconds = RandomUtils.nextLong(startInclusive, endExclusive);
		sleep(Duration.ofSeconds(seconds), resetInterruptFlagWhenInterrupted);
	}

	public static void sleepRandomMillis(long startInclusive, long endExclusive) {
		sleepRandomMillis(startInclusive, endExclusive, false);
	}

	public static void sleepRandomMillis(long startInclusive, long endExclusive, boolean resetInterruptFlagWhenInterrupted) {
		final var mills = RandomUtils.nextLong(startInclusive, endExclusive);
		sleep(Duration.ofMillis(mills), resetInterruptFlagWhenInterrupted);
	}

}
