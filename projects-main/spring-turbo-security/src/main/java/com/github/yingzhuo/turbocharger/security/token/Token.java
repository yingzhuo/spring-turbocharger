package com.github.yingzhuo.turbocharger.security.token;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Supplier;

@FunctionalInterface
public interface Token extends Comparable<Token>, Supplier<String>, Serializable {

	public String asString();

	@Override
	public default String get() {
		return this.asString();
	}

	@Override
	public default int compareTo(Token o) {
		return Objects.compare(this.asString(), o.asString(), Comparator.naturalOrder());
	}

}
