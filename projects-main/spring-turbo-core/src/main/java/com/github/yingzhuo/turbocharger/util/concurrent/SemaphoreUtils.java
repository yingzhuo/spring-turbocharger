package com.github.yingzhuo.turbocharger.util.concurrent;

import org.springframework.util.Assert;

import java.util.concurrent.Semaphore;

public final class SemaphoreUtils {

	private SemaphoreUtils() {
		super();
	}

	public static void acquire(Semaphore semaphore) {
		acquire(semaphore, false);
	}

	public static void acquire(Semaphore semaphore, boolean resetInterruptFlagWhenInterrupted) {
		Assert.notNull(semaphore, "semaphore is null");

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			if (resetInterruptFlagWhenInterrupted) {
				Thread.currentThread().interrupt();
			} else {
				throw new UncheckedInterruptedException(e);
			}
		}
	}

}
