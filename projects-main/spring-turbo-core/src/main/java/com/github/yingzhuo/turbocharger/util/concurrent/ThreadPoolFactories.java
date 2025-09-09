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
package com.github.yingzhuo.turbocharger.util.concurrent;

import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * 简单工具用于创建线程池
 *
 * @author 应卓
 * @see Thread
 * @see Runnable
 * @see Callable
 * @see FutureTask
 * @see CompletableFuture
 * @see org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean
 * @see org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean
 * @since 3.3.5
 */
public final class ThreadPoolFactories {

	/**
	 * 私有构造方法
	 */
	private ThreadPoolFactories() {
		super();
	}

	/**
	 * 创建线程数固定的线程池 <br>
	 * 本方法不适合用在严肃的生产环境
	 *
	 * @param n 线程数
	 * @return 线程池实例
	 */
	public static ExecutorService createFixed(int n) {
		return Executors.newFixedThreadPool(n);
	}

	/**
	 * 创建新的线程池
	 *
	 * @param corePoolSize      核心池容量
	 * @param maximumPoolSize   最大容量
	 * @param keepAlive         线程最大空闲时间
	 * @param blockingQueueSize 阻塞队列容量
	 * @return 线程池实例
	 */
	public static ExecutorService create(int corePoolSize, int maximumPoolSize, Duration keepAlive, int blockingQueueSize) {
		return create(corePoolSize, maximumPoolSize, keepAlive, blockingQueueSize, new ThreadPoolExecutor.AbortPolicy());
	}

	/**
	 * 创建新的线程池
	 *
	 * @param corePoolSize             核心池容量
	 * @param maximumPoolSize          最大容量
	 * @param keepAlive                线程最大空闲时间
	 * @param blockingQueueSize        阻塞队列容量
	 * @param rejectedExecutionHandler 拒绝策略
	 * @return 线程池实例
	 */
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
