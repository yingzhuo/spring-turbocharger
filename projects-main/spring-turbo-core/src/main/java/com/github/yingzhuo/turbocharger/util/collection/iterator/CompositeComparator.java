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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 组合型比较器
 *
 * @param <T> 泛型
 * @author 应卓
 */
public class CompositeComparator<T> implements Comparator<T> {

	private final List<Comparator<T>> comparators;

	@SafeVarargs
	public CompositeComparator(Comparator<T>... comparators) {
		Assert.notNull(comparators, "comparators is required");
		Assert.notEmpty(comparators, "comparators is required");
		Assert.noNullElements(comparators, "comparators has null element(s)");
		this.comparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(T o1, T o2) {
		for (Comparator<T> comparator : this.comparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

}
