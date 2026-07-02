package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public final class ObjectUtils {

	private ObjectUtils() {
	}

	@Nullable
	@SafeVarargs
	public static <T> T firstNonNull(@Nullable final T... values) {
		if (values != null) {
			for (final T val : values) {
				if (val != null) {
					return val;
				}
			}
		}
		return null;
	}

	@Nullable
	@SafeVarargs
	public static <T> T getFirstNonNull(@Nullable final Supplier<T>... suppliers) {
		if (suppliers != null) {
			for (final Supplier<T> supplier : suppliers) {
				if (supplier != null) {
					final T value = supplier.get();
					if (value != null) {
						return value;
					}
				}
			}
		}
		return null;
	}

	public static boolean anyNull(@Nullable Object... objects) {
		if (objects == null)
			return true;
		for (Object object : objects) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	public static boolean allNull(@Nullable Object... objects) {
		if (objects == null)
			return true;
		for (Object object : objects) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

}
