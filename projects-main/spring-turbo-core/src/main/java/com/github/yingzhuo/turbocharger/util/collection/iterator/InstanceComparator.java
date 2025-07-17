/*
 * Copyright 2025-present the original author or authors.
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

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;

/**
 * 按照对象类型排序的比较器
 *
 * @param <T> 泛型
 * @author 应卓
 * @deprecated 使用 {@link org.springframework.util.comparator.InstanceComparator} 替代
 */
@Deprecated
public class InstanceComparator<T> implements Comparator<T> {

	private final Class<?>[] instanceOrder;

	public InstanceComparator(List<Class<?>> instanceOrder) {
		this(instanceOrder.toArray(new Class<?>[0]));
	}

	public InstanceComparator(Class<?>... instanceOrder) {
		Assert.notNull(instanceOrder, "instanceOrder is null");
		this.instanceOrder = instanceOrder;
	}

	@Override
	public int compare(T o1, T o2) {
		int i1 = getOrder(o1);
		int i2 = getOrder(o2);
		return (Integer.compare(i1, i2));
	}

	private int getOrder(@Nullable T object) {
		if (object != null) {
			for (int i = 0; i < this.instanceOrder.length; i++) {
				if (this.instanceOrder[i].isInstance(object)) {
					return i;
				}
			}
		}
		return this.instanceOrder.length;
	}

}
