package com.github.yingzhuo.turbocharger.util.collection;

import org.jspecify.annotations.Nullable;
import org.springframework.core.OrderComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class SortingUtils {

	private SortingUtils() {
		super();
	}

	public static <T> void sort(T[] array) {
		sort(array, null);
	}

	public static <T> void sort(T[] array, @Nullable Comparator<? super T> comparator) {
		if (comparator != null) {
			Arrays.sort(array, comparator);
		} else {
			OrderComparator.sort(array);
		}
	}

	public static <T> void sort(List<T> list) {
		sort(list, null);
	}

	public static <T> void sort(List<T> list, @Nullable Comparator<? super T> comparator) {
		if (comparator != null) {
			list.sort(comparator);
		} else {
			OrderComparator.sort(list);
		}
	}
}
