package com.github.yingzhuo.turbocharger.util.hash;

import org.jspecify.annotations.Nullable;

public interface BloomFilter {

	public void add(String element);

	public boolean mightContain(@Nullable String element);

	public default boolean notContain(@Nullable String element) {
		return !mightContain(element);
	}

}
