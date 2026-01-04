/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 固定顺序比较器
 *
 * @param <T> 泛型
 * @author 应卓
 */
public class FixedOrderComparator<T> implements Comparator<T> {

	private final boolean greaterIfMissing;
	private final Object[] array;

	/**
	 * 构造方法
	 *
	 * @param objs 参与排序的数组，数组的元素位置决定了对象的排序先后
	 */
	public FixedOrderComparator(List<T> objs) {
		this(true, objs);
	}

	/**
	 * 构造方法
	 *
	 * @param greaterIfMissing 如果不在列表中是否排在后边
	 * @param objs             参与排序的数组，数组的元素位置决定了对象的排序先后
	 */
	public FixedOrderComparator(boolean greaterIfMissing, List<T> objs) {
		this(greaterIfMissing, objs.toArray(new Object[0]));
	}

	/**
	 * 构造方法
	 *
	 * @param objs 参与排序的数组，数组的元素位置决定了对象的排序先后
	 */
	public FixedOrderComparator(Object... objs) {
		this(true, objs);
	}

	/**
	 * 构造方法
	 *
	 * @param greaterIfMissing 如果不在列表中是否排在后边
	 * @param objs             参与排序的数组，数组的元素位置决定了对象的排序先后
	 */
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

	/**
	 * 查找对象类型所在列表的位置
	 *
	 * @param object 对象
	 * @return 位置，未找到位置根据{@link #greaterIfMissing}取不同值，false返回-1，否则返回列表长度
	 */
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
