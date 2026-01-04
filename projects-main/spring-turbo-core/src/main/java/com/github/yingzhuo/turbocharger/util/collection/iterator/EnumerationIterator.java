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

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Enumeration包装成Iterator
 *
 * @param <T> 泛型
 * @author 应卓
 * @see IteratorEnumeration
 * @since 1.2.2
 */
public class EnumerationIterator<T> implements Iterator<T> {

	private final Enumeration<T> innerEnumeration;

	public EnumerationIterator(Enumeration<T> enumeration) {
		Assert.notNull(enumeration, "enumeration is required");
		this.innerEnumeration = enumeration;
	}

	public static <T> EnumerationIterator<T> newInstance(Enumeration<T> inner) {
		return new EnumerationIterator<>(inner);
	}

	@Override
	public boolean hasNext() {
		return this.innerEnumeration.hasMoreElements();
	}

	@Override
	public T next() {
		return this.innerEnumeration.nextElement();
	}

}
