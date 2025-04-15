/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.util.collection;

import org.springframework.lang.Nullable;

import java.util.*;

/**
 * {@link List} 创建工具
 *
 * @author 应卓
 * @see SetFactories
 * @see StreamFactories
 * @since 1.0.9
 */
public final class ListFactories {

	/**
	 * 私有构造方法
	 */
	private ListFactories() {
		super();
	}

	@SafeVarargs
	public static <T> List<T> newUnmodifiableList(T... elements) {
		return Collections.unmodifiableList(newArrayList(elements));
	}

	@SafeVarargs
	public static <T> ArrayList<T> newArrayList(T... elements) {
		final var list = new ArrayList<T>();
		Collections.addAll(list, elements);
		return list;
	}

	@SafeVarargs
	public static <T> LinkedList<T> newLinkedList(T... elements) {
		return new LinkedList<T>(Arrays.asList(elements));
	}

	@SafeVarargs
	public static <T> Vector<T> newVector(T... elements) {
		return new Vector<T>(Arrays.asList(elements));
	}

	@SafeVarargs
	public static <T> ArrayList<T> nullSafeNewArrayList(@Nullable T... elements) {
		final var list = new ArrayList<T>();
		CollectionUtils.nullSafeAddAll(list, elements);
		return list;
	}

	@SafeVarargs
	public static <T> LinkedList<T> nullSafeNewLinkedList(@Nullable T... elements) {
		final var list = new LinkedList<T>();
		CollectionUtils.nullSafeAddAll(list, elements);
		return list;
	}

	@SafeVarargs
	public static <T> Vector<T> nullSafeNewVector(@Nullable T... elements) {
		final var list = new Vector<T>();
		CollectionUtils.nullSafeAddAll(list, elements);
		return list;
	}

}
