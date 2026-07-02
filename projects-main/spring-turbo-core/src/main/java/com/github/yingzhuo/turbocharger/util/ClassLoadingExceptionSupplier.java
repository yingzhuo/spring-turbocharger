package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

import java.util.function.Supplier;

public class ClassLoadingExceptionSupplier implements Supplier<ClassLoadingException> {

	private final String className;

	public ClassLoadingExceptionSupplier(String className) {
		Assert.hasText(className, "className is required");
		this.className = className;
	}

	@Override
	public ClassLoadingException get() {
		var msg = StringFormatter.format("not able to load class. class name: '{}'", className);
		return new ClassLoadingException(msg);
	}

}
