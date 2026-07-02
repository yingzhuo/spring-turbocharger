package com.github.yingzhuo.turbocharger.util.collection;

import org.jspecify.annotations.Nullable;

import java.util.*;

public final class ArrayUtils {

	private ArrayUtils() {
		super();
	}

	public static <T> int length(@Nullable T[] array) {
		return array != null ? array.length : 0;
	}

	public static <T> boolean isNullOrEmpty(@Nullable T[] array) {
		return array == null || array.length == 0;
	}

	public static <T> boolean doseNotContainsAnyElements(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return true;
		for (T obj : array) {
			if (obj != null)
				return false;
		}
		return true;
	}

	public static <T> List<T> toArrayList(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return new ArrayList<>(0);
		return new ArrayList<>(Arrays.asList(array));
	}

	public static <T> List<T> toUnmodifiableList(@Nullable T[] array) {
		return Collections.unmodifiableList(toArrayList(array));
	}

	public static <T> Set<T> toHashSet(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return new HashSet<>();
		return new HashSet<>(Arrays.asList(array));
	}

	public static <T> Set<T> toUnmodifiableSet(@Nullable T[] array) {
		return Collections.unmodifiableSet(toHashSet(array));
	}

	public static <T> boolean contains(@Nullable T[] array, T elementToFind) {
		return toUnmodifiableSet(array).contains(elementToFind);
	}

	@Nullable
	public static <T> T[] emptyToNull(@Nullable T[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		return array;
	}

}
