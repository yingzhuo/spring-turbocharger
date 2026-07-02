package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

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
