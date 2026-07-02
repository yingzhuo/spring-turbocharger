package com.github.yingzhuo.turbocharger.util;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {

	private RandomUtils() {
		super();
	}

	public static boolean nextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	public static byte[] nextBytes(int count) {
		byte[] result = new byte[count];
		ThreadLocalRandom.current().nextBytes(result);
		return result;
	}

	public static int nextInt(int startInclusive, int endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}
		return startInclusive + ThreadLocalRandom.current().nextInt(endExclusive - startInclusive);
	}

	public static int nextInt() {
		return nextInt(0, Integer.MAX_VALUE);
	}

	public static long nextLong(long startInclusive, long endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}
		return startInclusive + nextLong(endExclusive - startInclusive);
	}

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

	public static double nextDouble(double startInclusive, double endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}
		return startInclusive + ((endExclusive - startInclusive) * ThreadLocalRandom.current().nextDouble());
	}

	public static double nextDouble() {
		return nextDouble(0, Double.MAX_VALUE);
	}

	public static float nextFloat(float startInclusive, float endExclusive) {
		if (startInclusive == endExclusive) {
			return startInclusive;
		}
		return startInclusive + ((endExclusive - startInclusive) * ThreadLocalRandom.current().nextFloat());
	}

	public static float nextFloat() {
		return nextFloat(0, Float.MAX_VALUE);
	}

}
