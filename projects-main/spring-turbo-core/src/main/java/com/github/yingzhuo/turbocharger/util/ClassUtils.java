package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.reflection.InstanceUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Supplier;

public final class ClassUtils {

	@Nullable
	private static final ClassLoader DEFAULT_CLASSLOADER = org.springframework.util.ClassUtils.getDefaultClassLoader();

	private ClassUtils() {
		super();
	}

	public static ClassLoader getDefaultClassLoader() {
		// (only null if even the system ClassLoader isn't accessible)
		return Optional.ofNullable(DEFAULT_CLASSLOADER)
			.orElse(Thread.currentThread().getContextClassLoader());
	}

	public static Class<?> forNameElseThrow(String className) {
		return forNameElseThrow(className, new ClassLoadingExceptionSupplier(className));
	}

	public static Class<?> forNameElseThrow(String className,
											Supplier<? extends RuntimeException> exceptionIfCannotLoad) {
		Assert.notNull(exceptionIfCannotLoad, "exceptionIfCannotLoad is required");
		return forName(className).orElseThrow(exceptionIfCannotLoad);
	}

	public static Optional<Class<?>> forName(String className) {
		try {
			Assert.hasText(className, "className is required");
			final Class<?> clazz = org.springframework.util.ClassUtils.forName(className, DEFAULT_CLASSLOADER);
			return Optional.of(clazz);
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	public static boolean isPresent(String className) {
		try {
			Assert.hasText(className, "className is required");
			return org.springframework.util.ClassUtils.isPresent(className, DEFAULT_CLASSLOADER);
		} catch (IllegalAccessError e) {
			// 典型情况，该类型的依赖有缺失
			return false;
		}
	}

	public static boolean isAbsent(String className) {
		return !isPresent(className);
	}

	public static String getPackageName(Class<?> clz) {
		return org.springframework.util.ClassUtils.getPackageName(clz);
	}

	public static String getPackageName(String className) {
		return org.springframework.util.ClassUtils.getPackageName(className);
	}

}
