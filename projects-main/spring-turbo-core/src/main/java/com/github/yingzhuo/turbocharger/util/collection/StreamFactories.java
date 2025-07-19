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

import com.github.yingzhuo.turbocharger.util.collection.iterator.EnumerationIterator;
import org.springframework.lang.Nullable;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Stream} 创建工具
 *
 * @author 应卓
 * @see SetFactories
 * @see ListFactories
 * @since 1.0.13
 */
public final class StreamFactories {

	/**
	 * 私有构造方法
	 */
	private StreamFactories() {
		super();
	}

	@SafeVarargs
	public static <T> Stream<T> nullSafeNewStream(@Nullable T... elements) {
		return ListFactories.nullSafeNewArrayList(elements).stream();
	}

	public static <T> Stream<T> of(@Nullable Iterator<T> iterator) {
		return of(iterator, false);
	}

	public static <T> Stream<T> of(@Nullable Iterator<T> iterator, boolean parallel) {
		if (iterator == null) {
			return Stream.empty();
		}
		final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
		return StreamSupport.stream(spliterator, parallel);
	}

	public static <T> Stream<T> of(@Nullable Enumeration<T> enumeration) {
		return of(enumeration, false);
	}

	public static <T> Stream<T> of(@Nullable Enumeration<T> enumeration, boolean parallel) {
		if (enumeration == null) {
			return Stream.empty();
		}
		final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(new EnumerationIterator<>(enumeration),
			0);
		return StreamSupport.stream(spliterator, parallel);
	}

}
