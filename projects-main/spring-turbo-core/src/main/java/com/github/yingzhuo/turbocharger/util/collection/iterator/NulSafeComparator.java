package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;

@Deprecated
@SuppressWarnings({"rawtypes", "unchecked"})
public class NulSafeComparator<T> implements Comparator<T> {

	private final boolean nullGreater;

	@Nullable
	private final Comparator<T> comparator;

	public NulSafeComparator(boolean nullGreater) {
		this(nullGreater, null);
	}

	public NulSafeComparator(boolean nullGreater, @Nullable Comparator<? super T> comparator) {
		this.nullGreater = nullGreater;
		this.comparator = (Comparator<T>) comparator;
	}

	@Override
	public int compare(@Nullable T a, @Nullable T b) {
		if (a == b) {
			return 0;
		}
		if (a == null) {
			return nullGreater ? 1 : -1;
		} else if (b == null) {
			return nullGreater ? -1 : 1;
		} else {
			return doCompare(a, b);
		}
	}

	@Override
	public Comparator<T> thenComparing(Comparator<? super T> other) {
		Objects.requireNonNull(other);
		return new NulSafeComparator<>(nullGreater, comparator == null ? other : comparator.thenComparing(other));
	}

	@Override
	public Comparator<T> reversed() {
		return new NulSafeComparator<>((!nullGreater), comparator == null ? null : comparator.reversed());
	}

	private int doCompare(T a, T b) {
		if (null == comparator) {
			if (a instanceof Comparable && b instanceof Comparable) {
				return ((Comparable) a).compareTo(b);
			}
			return 0;
		}

		return comparator.compare(a, b);
	}
}
