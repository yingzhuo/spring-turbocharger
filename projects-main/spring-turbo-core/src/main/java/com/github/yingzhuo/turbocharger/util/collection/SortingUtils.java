/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util.collection;

import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 排序工具
 *
 * @author 应卓
 * @since 3.4.4
 */
public final class SortingUtils {

	/**
	 * 私有构造方法
	 */
	private SortingUtils() {
		super();
	}

	/**
	 * 为数组排序
	 *
	 * @param array 数组
	 */
	public static <T> void sort(T[] array) {
		sort(array, null);
	}

	/**
	 * 为数组排序
	 *
	 * @param array      数组
	 * @param comparator 比较器
	 */
	public static <T> void sort(T[] array, @Nullable Comparator<? super T> comparator) {
		if (comparator != null) {
			Arrays.sort(array, comparator);
		} else {
			OrderComparator.sort(array);
		}
	}

	/**
	 * 为列表排序
	 *
	 * @param list 数组
	 */
	public static <T> void sort(List<T> list) {
		sort(list, null);
	}

	/**
	 * 为列表排序
	 *
	 * @param list       数组
	 * @param comparator 比较器
	 */
	public static <T> void sort(List<T> list, @Nullable Comparator<? super T> comparator) {
		if (comparator != null) {
			list.sort(comparator);
		} else {
			OrderComparator.sort(list);
		}
	}
}
