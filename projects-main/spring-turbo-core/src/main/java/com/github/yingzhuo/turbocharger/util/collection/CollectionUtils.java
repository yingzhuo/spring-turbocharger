package com.github.yingzhuo.turbocharger.util.collection;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class CollectionUtils {

	private CollectionUtils() {
		super();
	}

	public static <T> int size(@Nullable Collection<T> collection) {
		return collection != null ? collection.size() : 0;
	}

	public static <K, V> int size(@Nullable Map<K, V> map) {
		return map != null ? map.size() : 0;
	}

	public static <T> boolean isEmpty(@Nullable Collection<T> collection) {
		return size(collection) == 0;
	}

	public static <K, V> boolean isEmpty(@Nullable Map<K, V> map) {
		return size(map) == 0;
	}

	public static <T> boolean isNotEmpty(@Nullable Collection<T> collection) {
		return size(collection) != 0;
	}

	public static <K, V> boolean isNotEmpty(@Nullable Map<K, V> map) {
		return size(map) != 0;
	}

	public static <T> void nullSafeAdd(Collection<T> collection, @Nullable T element) {
		Assert.notNull(collection, "collection is required");
		if (element != null) {
			collection.add(element);
		}
	}

	public static <T> void nullSafeAddAll(Collection<T> collection, @Nullable T[] elements) {
		Assert.notNull(collection, "collection is required");
		if (elements != null) {
			for (T obj : elements) {
				Optional.ofNullable(obj).ifPresent(collection::add);
			}
		}
	}

	public static <T> void nullSafeAddAll(Collection<T> collection, @Nullable Collection<T> elements) {
		Assert.notNull(collection, "collection is required");
		if (elements != null) {
			for (T obj : elements) {
				Optional.ofNullable(obj).ifPresent(collection::add);
			}
		}
	}

	public static <K, V> void nullSafeAddAll(Map<K, V> map, @Nullable Map<K, V> elements) {
		Assert.notNull(map, "map is required");
		if (elements != null) {
			map.putAll(elements);
		}
	}

}
