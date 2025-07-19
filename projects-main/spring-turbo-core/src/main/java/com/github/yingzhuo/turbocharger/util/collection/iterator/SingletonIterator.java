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
package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.springframework.lang.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 只有单个元素的迭代器
 *
 * @param <T> 元素类型泛型
 * @author 应卓
 * @since 3.3.1
 */
public class SingletonIterator<T> implements Iterator<T> {

	@Nullable
	private T element;

	public SingletonIterator(@Nullable T element) {
		this.element = element;
	}

	public static <T> SingletonIterator<T> of(@Nullable T element) {
		return new SingletonIterator<>(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return element != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T next() {
		if (element != null) {
			var e = element;
			element = null;
			return e;
		} else {
			throw new NoSuchElementException();
		}
	}

}
