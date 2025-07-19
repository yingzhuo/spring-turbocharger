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
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * 二元组
 *
 * @author 应卓
 * @see Tuple
 * @see #ofNullable(Object, Object)
 * @see #ofNonNull(Object, Object)
 * @since 1.0.0
 */
public final class Pair<A, B> implements Serializable {

	@Nullable
	private final A a;

	@Nullable
	private final B b;

	private Pair(@Nullable A a, @Nullable B b) {
		this.a = a;
		this.b = b;
	}

	public static <A, B> Pair<A, B> ofNullable(@Nullable A a, @Nullable B b) {
		return new Pair<>(a, b);
	}

	public static <A, B> Pair<A, B> ofNonNull(A a, B b) {
		Assert.notNull(a, "a is null");
		Assert.notNull(b, "b is null");
		return new Pair<>(a, b);
	}

	@Nullable
	public A getA() {
		return a;
	}

	public A getRequiredA() {
		return Objects.requireNonNull(a);
	}

	@Nullable
	public B getB() {
		return b;
	}

	public B getRequiredB() {
		return Objects.requireNonNull(b);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public String toString() {
		return StringFormatter.format("({}, {})", a, b);
	}

}
