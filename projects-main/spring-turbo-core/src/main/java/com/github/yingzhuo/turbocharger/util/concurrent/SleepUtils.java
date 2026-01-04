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
package com.github.yingzhuo.turbocharger.util.concurrent;

import com.github.yingzhuo.turbocharger.util.RandomUtils;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * 线程休眠工具
 *
 * @author 应卓
 * @see ThreadUtils
 * @see CurrentThreadUtils
 * @since 3.3.5
 */
public final class SleepUtils {

	/**
	 * 私有构造方法
	 */
	private SleepUtils() {
		super();
	}

	/**
	 * 暂停当前线程
	 *
	 * @param duration 暂停时间
	 */
	public static void sleep(Duration duration) {
		sleep(duration, false);
	}

	/**
	 * 暂停当前线程
	 *
	 * @param duration                          暂停时间
	 * @param resetInterruptFlagWhenInterrupted 线程暂停被打断时是否重置中断标志位
	 */
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

	/**
	 * 暂停当前线程随机n秒
	 *
	 * @param startInclusive 最小休息秒数 (包含)
	 * @param endExclusive   最大休息秒数 (不包含)
	 * @see RandomUtils
	 */
	public static void sleepRandomSeconds(long startInclusive, long endExclusive) {
		sleepRandomSeconds(startInclusive, endExclusive, false);
	}

	/**
	 * 暂停当前线程随机n秒
	 *
	 * @param startInclusive                    最小休息秒数 (包含)
	 * @param endExclusive                      最大休息秒数 (不包含)
	 * @param resetInterruptFlagWhenInterrupted 线程暂停被打断时是否重置中断标志位
	 * @see RandomUtils
	 */
	public static void sleepRandomSeconds(long startInclusive, long endExclusive, boolean resetInterruptFlagWhenInterrupted) {
		final var seconds = RandomUtils.nextLong(startInclusive, endExclusive);
		sleep(Duration.ofSeconds(seconds), resetInterruptFlagWhenInterrupted);
	}

	/**
	 * 暂停当前线程随机n毫秒
	 *
	 * @param startInclusive 最小休息秒数 (包含)
	 * @param endExclusive   最大休息秒数 (不包含)
	 * @see RandomUtils
	 */
	public static void sleepRandomMillis(long startInclusive, long endExclusive) {
		sleepRandomMillis(startInclusive, endExclusive, false);
	}

	/**
	 * 暂停当前线程随机n毫秒
	 *
	 * @param startInclusive                    最小休息秒数 (包含)
	 * @param endExclusive                      最大休息秒数 (不包含)
	 * @param resetInterruptFlagWhenInterrupted 线程暂停被打断时是否重置中断标志位
	 * @see RandomUtils
	 */
	public static void sleepRandomMillis(long startInclusive, long endExclusive, boolean resetInterruptFlagWhenInterrupted) {
		final var mills = RandomUtils.nextLong(startInclusive, endExclusive);
		sleep(Duration.ofMillis(mills), resetInterruptFlagWhenInterrupted);
	}

}
