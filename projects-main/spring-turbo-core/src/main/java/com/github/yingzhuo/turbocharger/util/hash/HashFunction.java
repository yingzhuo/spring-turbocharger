package com.github.yingzhuo.turbocharger.util.hash;

import java.util.function.Function;

@FunctionalInterface
public interface HashFunction extends Function<String, Integer> {

	@Override
	public Integer apply(String key);

}
