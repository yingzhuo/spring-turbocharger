package com.github.yingzhuo.turbocharger.function;

@FunctionalInterface
public interface Customizer<T> {

	public void customize(T component);

}
