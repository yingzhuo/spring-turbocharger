package com.github.yingzhuo.turbocharger.util.concurrent;

import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.util.concurrent.*;

public final class ThreadPoolFactories {

	private ThreadPoolFactories() {
		super();
	}

	public static ExecutorService createFixed(int n) {
		return Executors.newFixedThreadPool(n);
	}

	public static ExecutorService create(int corePoolSize, int maximumPoolSize, Duration keepAlive, int blockingQueueSize) {
		return create(corePoolSize, maximumPoolSize, keepAlive, blockingQueueSize, new ThreadPoolExecutor.AbortPolicy());
	}

	public static ExecutorService create(int corePoolSize, int maximumPoolSize, Duration keepAlive, int blockingQueueSize, @Nullable RejectedExecutionHandler rejectedExecutionHandler) {
		return new ThreadPoolExecutor(
			corePoolSize,
			maximumPoolSize,
			keepAlive.toMillis(),
			TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<>(blockingQueueSize),
			Executors.defaultThreadFactory(),
			rejectedExecutionHandler != null ? rejectedExecutionHandler : new ThreadPoolExecutor.AbortPolicy()
		);
	}

}
