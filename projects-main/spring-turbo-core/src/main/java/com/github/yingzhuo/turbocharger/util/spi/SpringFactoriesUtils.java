package com.github.yingzhuo.turbocharger.util.spi;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class SpringFactoriesUtils {

	private SpringFactoriesUtils() {
		super();
	}

	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null, null, null);
	}

	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation) {
		return load(targetType, springFactoriesResourceLocation, null, null);
	}

	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation, @Nullable ClassLoader classLoader) {
		return load(targetType, springFactoriesResourceLocation, classLoader, null);
	}

	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation, @Nullable ClassLoader classLoader, @Nullable Predicate<Class<?>> filter) {
		Assert.notNull(targetType, "targetType must not be null");

		Predicate<Class<?>> predicateToUse = filter != null ? filter : c -> true;

		return SpringFactoriesLoader.forResourceLocation(
				springFactoriesResourceLocation != null ? springFactoriesResourceLocation : SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION,
				classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader()
			)
			.load(targetType)
			.stream()
			.filter(t -> predicateToUse.test(t.getClass()));
	}

}
