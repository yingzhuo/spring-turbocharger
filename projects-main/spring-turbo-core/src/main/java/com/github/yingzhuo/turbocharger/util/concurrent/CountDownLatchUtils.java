package com.github.yingzhuo.turbocharger.util.concurrent;

import org.springframework.util.Assert;

import java.util.concurrent.CountDownLatch;

public final class CountDownLatchUtils {

	private CountDownLatchUtils() {
		super();
	}

	public static void await(CountDownLatch countDownLatch) {
		await(countDownLatch, false);
	}

	public static void await(CountDownLatch countDownLatch, boolean resetInterruptFlagWhenInterrupted) {
		Assert.notNull(countDownLatch, "countDownLatch is null");

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			if (resetInterruptFlagWhenInterrupted) {
				Thread.currentThread().interrupt();
			} else {
				throw new UncheckedInterruptedException(e);
			}
		}
	}

	public static void countDown(CountDownLatch countDownLatch) {
		Assert.notNull(countDownLatch, "countDownLatch is null");
		countDownLatch.countDown();
	}

}
