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
package com.github.yingzhuo.turbocharger.util;

import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * {@link Object} 相关工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class ObjectUtils {

	/**
	 * 私有构造方法
	 */
	private ObjectUtils() {
	}

	/**
	 * 查找第一个非空值
	 *
	 * @param values 要查找的对象
	 * @param <T>    对象类型泛型
	 * @return 查钊结果或 {@code null}
	 */
	@Nullable
	@SafeVarargs
	public static <T> T firstNonNull(@Nullable final T... values) {
		if (values != null) {
			for (final T val : values) {
				if (val != null) {
					return val;
				}
			}
		}
		return null;
	}

	/**
	 * 查找第一个非空值
	 *
	 * @param suppliers 要查找的对象的提供器
	 * @param <T>       对象类型泛型
	 * @return 查钊结果或 {@code null}
	 */
	@Nullable
	@SafeVarargs
	public static <T> T getFirstNonNull(@Nullable final Supplier<T>... suppliers) {
		if (suppliers != null) {
			for (final Supplier<T> supplier : suppliers) {
				if (supplier != null) {
					final T value = supplier.get();
					if (value != null) {
						return value;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断数组中是否存在 {@code null} 值
	 *
	 * @param objects 数组
	 * @return 存在 {@code null} 值时返回 {@code true} 否则返回 {@code false}
	 * @since 1.0.6
	 */
	public static boolean anyNull(@Nullable Object... objects) {
		if (objects == null)
			return true;
		for (Object object : objects) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断数组中是否全部为 {@code null} 值
	 *
	 * @param objects 数组
	 * @return 全部为 {@code null} 值时返回 {@code true} 否则返回 {@code false}
	 * @since 1.0.6
	 */
	public static boolean allNull(@Nullable Object... objects) {
		if (objects == null)
			return true;
		for (Object object : objects) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

}
