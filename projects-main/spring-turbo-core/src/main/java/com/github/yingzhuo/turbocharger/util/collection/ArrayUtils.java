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

import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 数组相关工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class ArrayUtils {

	/**
	 * 私有构造方法
	 */
	private ArrayUtils() {
	}

	/**
	 * 获取数组长度
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 长度或0
	 */
	public static <T> int length(@Nullable T[] array) {
		return array != null ? array.length : 0;
	}

	/**
	 * 判断数组是否为 {@code null} 或者 长度为0
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 长度为0或数组为 {@code null} 返回 {@code true} 其他情况返回 {@code false}
	 */
	public static <T> boolean isNullOrEmpty(@Nullable T[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * 判断数组是否不包含任何元素
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 数组不包含任何元素时返回 {@code true} 否则返回 {@code false}
	 */
	public static <T> boolean doseNotContainsAnyElements(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return true;
		for (T obj : array) {
			if (obj != null)
				return false;
		}
		return true;
	}

	/**
	 * 数组转换成{@link ArrayList}
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 结果
	 */
	public static <T> List<T> toArrayList(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return new ArrayList<>(0);
		return new ArrayList<>(Arrays.asList(array));
	}

	/**
	 * 数组转换成{@link ArrayList} (不可变)
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 结果
	 */
	public static <T> List<T> toUnmodifiableList(@Nullable T[] array) {
		return Collections.unmodifiableList(toArrayList(array));
	}

	/**
	 * 数组转换成{@link HashSet}
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 结果
	 */
	public static <T> Set<T> toHashSet(@Nullable T[] array) {
		if (isNullOrEmpty(array))
			return new HashSet<>();
		return new HashSet<>(Arrays.asList(array));
	}

	/**
	 * 数组转换成{@link HashSet} (不可变)
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 结果
	 */
	public static <T> Set<T> toUnmodifiableSet(@Nullable T[] array) {
		return Collections.unmodifiableSet(toHashSet(array));
	}

	/**
	 * 判断字符数组是否包含指定的元素
	 *
	 * @param array         数组
	 * @param elementToFind 要查找的元素
	 * @param <T>           数组元素类型
	 * @return 结果
	 */
	public static <T> boolean contains(@Nullable T[] array, T elementToFind) {
		return toUnmodifiableSet(array).contains(elementToFind);
	}

	/**
	 * 没有数据的数组转成空值
	 *
	 * @param array 数组
	 * @param <T>   数组元素类型
	 * @return 结果
	 */
	@Nullable
	public static <T> T[] emptyToNull(@Nullable T[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		return array;
	}

}
