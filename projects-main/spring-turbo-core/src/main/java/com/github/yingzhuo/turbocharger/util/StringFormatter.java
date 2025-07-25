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
package com.github.yingzhuo.turbocharger.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串拼凑工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class StringFormatter {

	private static final char DELIM_START = '{';
	private static final String DELIM_STR = "{}";
	private static final char ESCAPE_CHAR = '\\';

	private StringFormatter() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String format(String messagePattern) {
		return messagePattern;
	}

	public static String format(String messagePattern, Object arg) {
		return format(messagePattern, new Object[]{arg});
	}

	public static String format(String messagePattern, Object... argArray) {
		Throwable throwableCandidate = StringFormatter.getThrowableCandidate(argArray);
		Object[] args = argArray;
		if (throwableCandidate != null) {
			args = StringFormatter.trimmedCopy(argArray);
		}
		return format(messagePattern, args, throwableCandidate);
	}

	private static String format(final String messagePattern, final Object[] argArray, Throwable throwable) {

		if (messagePattern == null) {
			return null;
		}

		if (argArray == null) {
			return messagePattern;
		}

		int i = 0;
		int j;
		StringBuilder builder = new StringBuilder(messagePattern.length() + 50);

		int L;
		for (L = 0; L < argArray.length; L++) {

			j = messagePattern.indexOf(DELIM_STR, i);

			if (j == -1) {
				if (i == 0) { // this is a simple string
					return messagePattern;
				} else {
					builder.append(messagePattern, i, messagePattern.length());
					return messagePattern;
				}
			} else {
				if (isEscapedDelimiter(messagePattern, j)) {
					if (!isDoubleEscaped(messagePattern, j)) {
						L--;
						builder.append(messagePattern, i, j - 1);
						builder.append(DELIM_START);
						i = j + 1;
					} else {
						builder.append(messagePattern, i, j - 1);
						deeplyAppendParameter(builder, argArray[L], new HashMap<>());
						i = j + 2;
					}
				} else {
					builder.append(messagePattern, i, j);
					deeplyAppendParameter(builder, argArray[L], new HashMap<>());
					i = j + 2;
				}
			}
		}
		builder.append(messagePattern, i, messagePattern.length());
		return builder.toString();
	}

	private static boolean isEscapedDelimiter(String messagePattern, int delimiterStartIndex) {
		if (delimiterStartIndex == 0) {
			return false;
		}
		char potentialEscape = messagePattern.charAt(delimiterStartIndex - 1);
		return potentialEscape == ESCAPE_CHAR;
	}

	private static boolean isDoubleEscaped(String messagePattern, int delimiterStartIndex) {
		return delimiterStartIndex >= 2 && messagePattern.charAt(delimiterStartIndex - 2) == ESCAPE_CHAR;
	}

	private static void deeplyAppendParameter(StringBuilder builder, Object o, Map<Object[], Object> seenMap) {
		if (o == null) {
			builder.append("null");
			return;
		}
		if (!o.getClass().isArray()) {
			safeObjectAppend(builder, o);
		} else {
			if (o instanceof boolean[]) {
				booleanArrayAppend(builder, (boolean[]) o);
			} else if (o instanceof byte[]) {
				byteArrayAppend(builder, (byte[]) o);
			} else if (o instanceof char[]) {
				charArrayAppend(builder, (char[]) o);
			} else if (o instanceof short[]) {
				shortArrayAppend(builder, (short[]) o);
			} else if (o instanceof int[]) {
				intArrayAppend(builder, (int[]) o);
			} else if (o instanceof long[]) {
				longArrayAppend(builder, (long[]) o);
			} else if (o instanceof float[]) {
				floatArrayAppend(builder, (float[]) o);
			} else if (o instanceof double[]) {
				doubleArrayAppend(builder, (double[]) o);
			} else {
				objectArrayAppend(builder, (Object[]) o, seenMap);
			}
		}
	}

	private static void safeObjectAppend(StringBuilder builder, Object o) {
		try {
			String oAsString = o.toString();
			builder.append(oAsString);
		} catch (Throwable t) {
			builder.append("[FAILED toString()]");
		}
	}

	private static void objectArrayAppend(StringBuilder builder, Object[] a, Map<Object[], Object> seenMap) {
		builder.append('[');
		if (!seenMap.containsKey(a)) {
			seenMap.put(a, null);
			final int len = a.length;
			for (int i = 0; i < len; i++) {
				deeplyAppendParameter(builder, a[i], seenMap);
				if (i != len - 1)
					builder.append(", ");
			}
			seenMap.remove(a);
		} else {
			builder.append("...");
		}
		builder.append(']');
	}

	private static void booleanArrayAppend(StringBuilder builder, boolean[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void byteArrayAppend(StringBuilder builder, byte[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void charArrayAppend(StringBuilder builder, char[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void shortArrayAppend(StringBuilder builder, short[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void intArrayAppend(StringBuilder builder, int[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void longArrayAppend(StringBuilder builder, long[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void floatArrayAppend(StringBuilder builder, float[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	private static void doubleArrayAppend(StringBuilder builder, double[] a) {
		builder.append('[');
		final int len = a.length;
		for (int i = 0; i < len; i++) {
			builder.append(a[i]);
			if (i != len - 1)
				builder.append(", ");
		}
		builder.append(']');
	}

	public static Throwable getThrowableCandidate(final Object[] argArray) {
		if (argArray == null || argArray.length == 0) {
			return null;
		}

		final Object lastEntry = argArray[argArray.length - 1];
		if (lastEntry instanceof Throwable) {
			return (Throwable) lastEntry;
		}

		return null;
	}

	public static Object[] trimmedCopy(final Object[] argArray) {
		if (argArray == null || argArray.length == 0) {
			throw new IllegalStateException("empty or null argument array");
		}
		final int trimmedLen = argArray.length - 1;
		Object[] trimmed = new Object[trimmedLen];
		if (trimmedLen > 0) {
			System.arraycopy(argArray, 0, trimmed, 0, trimmedLen);
		}
		return trimmed;
	}

}
