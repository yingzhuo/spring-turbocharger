package com.github.yingzhuo.turbocharger.util.spi;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ServiceLoaderUtils {

	private ServiceLoaderUtils() {
		super();
	}

	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null, null);
	}

	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader) {
		return load(targetType, classLoader, null);
	}

	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader, @Nullable Predicate<Class<?>> filter) {
		Assert.notNull(targetType, "targetType must not be null");

		Predicate<Class<?>> predicateToUse = filter != null ? filter : c -> true;
		return ServiceLoader.load(targetType, classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader())
			.stream()
			.filter(p -> predicateToUse.test(p.type())).map(ServiceLoader.Provider::get);
	}

}
