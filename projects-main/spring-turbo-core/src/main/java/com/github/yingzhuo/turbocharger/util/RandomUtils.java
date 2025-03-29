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
package com.github.yingzhuo.turbocharger.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数生成工具
 *
 * @author 应卓
 * @see RandomStringUtils
 * @since 1.0.0
 */
public final class RandomUtils {

	/**
	 * 私有构造方法
	 */
	private RandomUtils() {
	}

	/**
	 * 随机生成布尔值
	 *
	 * @return 随机布尔值
	 */
	public static boolean nextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	/**
	 * 随机生成字节数组
	 *
	 * @param count 字符数组长度
	 * @return 随机字节数组
	 */
	public static byte[] nextBytes(int count) {
		byte[] result = new byte[count];
		ThreadLocalRandom.current().nextBytes(result);
		return result;
	}

	/**
	 * 随机生成整数
	 *
	 * @param startInclusive 开始 (包含)
	 * @param endExclusive   结束 (不包含)
	 * @return 随机整数
	 */
	public static int nextInt(int startInclusive, int endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return startInclusive + ThreadLocalRandom.current().nextInt(endExclusive - startInclusive);
	}

	/**
	 * 随机生成整数
	 *
	 * @return 随机整数
	 */
	public static int nextInt() {
		return nextInt(0, Integer.MAX_VALUE);
	}

	/**
	 * 随机生成长整数
	 *
	 * @param startInclusive 开始 (包含)
	 * @param endExclusive   结束 (不包含)
	 * @return 随机长整数
	 */
	public static long nextLong(long startInclusive, long endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return startInclusive + nextLong(endExclusive - startInclusive);
	}

	/**
	 * 随机生成长整数
	 *
	 * @return 随机长整数
	 */
	public static long nextLong() {
		return nextLong(Long.MAX_VALUE);
	}

	private static long nextLong(long n) {
		long bits;
		long val;
		do {
			bits = ThreadLocalRandom.current().nextLong() >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0);

		return val;
	}

	/**
	 * 随机生成双精度数
	 *
	 * @param startInclusive 开始 (包含)
	 * @param endExclusive   结束 (不包含)
	 * @return 随机双精度数
	 */
	public static double nextDouble(double startInclusive, double endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return startInclusive + ((endExclusive - startInclusive) * ThreadLocalRandom.current().nextDouble());
	}

	/**
	 * 随机生成双精度数
	 *
	 * @return 随机双精度数
	 */
	public static double nextDouble() {
		return nextDouble(0, Double.MAX_VALUE);
	}

	/**
	 * 随机生成浮点数数
	 *
	 * @param startInclusive 开始 (包含)
	 * @param endExclusive   结束 (不包含)
	 * @return 随机浮点数
	 */
	public static float nextFloat(float startInclusive, float endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return startInclusive + ((endExclusive - startInclusive) * ThreadLocalRandom.current().nextFloat());
	}

	/**
	 * 随机生成浮点数数
	 *
	 * @return 随机浮点数
	 */
	public static float nextFloat() {
		return nextFloat(0, Float.MAX_VALUE);
	}

}
