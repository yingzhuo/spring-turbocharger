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
package com.github.yingzhuo.turbocharger.util.collection.iterator;

import org.springframework.util.Assert;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Iterator包装成Enumeration
 *
 * @param <T> 泛型
 * @author 应卓
 * @see EnumerationIterator
 * @since 1.2.2
 */
public class IteratorEnumeration<T> implements Enumeration<T> {

	private final Iterator<T> inner;

	public IteratorEnumeration(Iterator<T> iterator) {
		Assert.notNull(iterator, "iterator is required");
		this.inner = iterator;
	}

	public static <T> IteratorEnumeration<T> newInstance(Iterator<T> inner) {
		return new IteratorEnumeration<>(inner);
	}

	@Override
	public boolean hasMoreElements() {
		return inner.hasNext();
	}

	@Override
	public T nextElement() {
		return inner.next();
	}

}
