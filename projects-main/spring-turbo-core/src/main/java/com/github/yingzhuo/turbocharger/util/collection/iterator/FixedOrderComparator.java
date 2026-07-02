package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FixedOrderComparator<T> implements Comparator<T> {

	private final boolean greaterIfMissing;
	private final Object[] array;

	public FixedOrderComparator(List<T> objs) {
		this(true, objs);
	}

	public FixedOrderComparator(boolean greaterIfMissing, List<T> objs) {
		this(greaterIfMissing, objs.toArray(new Object[0]));
	}

	public FixedOrderComparator(Object... objs) {
		this(true, objs);
	}

	public FixedOrderComparator(boolean greaterIfMissing, Object... objs) {
		Assert.notNull(objs, "objs is null");
		Assert.notEmpty(objs, "objs is empty");
		this.greaterIfMissing = greaterIfMissing;
		this.array = objs;
	}

	@Override
	public int compare(T o1, T o2) {
		final int index1 = getOrder(o1);
		final int index2 = getOrder(o2);
		return Integer.compare(index1, index2);
	}

	private int getOrder(T object) {
		int order = -1;

		for (int i = 0; i < array.length; i++) {
			if (Objects.equals(array[i], object)) {
				order = i;
				break;
			}
		}

		if (order < 0) {
			order = this.greaterIfMissing ? this.array.length : -1;
		}
		return order;
	}

}
