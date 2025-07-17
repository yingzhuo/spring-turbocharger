/*
 * Copyright 2025-present the original author or authors.
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

import org.springframework.util.Assert;

import java.util.concurrent.Semaphore;

/**
 * {@link Semaphore} 相关工具
 *
 * @author 应卓
 * @see CountDownLatchUtils
 * @since 3.3.5
 */
public final class SemaphoreUtils {

	/**
	 * 私有构造方法
	 */
	private SemaphoreUtils() {
	}

	/**
	 * 尝试获取信号量
	 *
	 * @param semaphore 信号量
	 */
	public static void acquire(Semaphore semaphore) {
		acquire(semaphore, false);
	}

	/**
	 * 尝试获取信号量
	 *
	 * @param semaphore                         信号量
	 * @param resetInterruptFlagWhenInterrupted 线程暂停被打断时是否重置中断标志位
	 */
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
