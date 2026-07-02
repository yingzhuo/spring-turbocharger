package com.github.yingzhuo.turbocharger.util.collection.iterator;

import java.util.Comparator;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class NoopComparator<T> implements Comparator<T> {

	private NoopComparator() {
		super();
	}

	public static <T> NoopComparator<T> getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public int compare(T o1, T o2) {
		return 0;
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final NoopComparator INSTANCE = new NoopComparator();
	}

}
