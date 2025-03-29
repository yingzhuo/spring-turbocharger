/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.util.concurrent;

import org.springframework.util.Assert;

import java.util.concurrent.CountDownLatch;

/**
 * {@link CountDownLatchUtils}
 *
 * @author 应卓
 * @see SemaphoreUtils
 * @since 3.3.5
 */
public final class CountDownLatchUtils {

	/**
	 * 私有构造方法
	 */
	private CountDownLatchUtils() {
	}

	/**
	 * 等待倒计数结束
	 *
	 * @param countDownLatch countDownLatch 实例
	 */
	public static void await(CountDownLatch countDownLatch) {
		await(countDownLatch, false);
	}

	/**
	 * 等待倒计数结束
	 *
	 * @param countDownLatch                    countDownLatch实例
	 * @param resetInterruptFlagWhenInterrupted 线程暂停被打断时是否重置中断标志位
	 */
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

	/**
	 * 倒计数
	 *
	 * @param countDownLatch countDownLatch实例
	 */
	public static void countDown(CountDownLatch countDownLatch) {
		Assert.notNull(countDownLatch, "countDownLatch is null");
		countDownLatch.countDown();
	}

}
