package com.github.yingzhuo.turbocharger.util.reflection;

import com.github.yingzhuo.turbocharger.util.ClassUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.ReflectionUtils;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public final class InstanceUtils {

	private InstanceUtils() {
		super();
	}

	public static <T> T newInstanceElseThrow(Class<T> type) {
		return newInstanceElseThrow(type, new InstantiationExceptionSupplier(type));
	}

	public static <T> T newInstanceElseThrow(Class<T> type,
											 Supplier<? extends RuntimeException> exceptionIfCannotCreateInstance) {
		return newInstance(type).orElseThrow(exceptionIfCannotCreateInstance);
	}

	public static <T> Optional<T> newInstance(Class<T> type) {
		try {
			var constructor = ReflectionUtils.accessibleConstructor(type);
			ReflectionUtils.makeAccessible(constructor);
			return Optional.of(constructor.newInstance());
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	public static <T> Optional<T> newInstance(String className) {
		try {
			final Class<?> type = ClassUtils.forNameElseThrow(className);
			return (Optional<T>) newInstance(type);
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	@Nullable
	public static <T> T newInstanceElse(Class<T> type, @Nullable T defaultInstance) {
		return newInstance(type).orElse(defaultInstance);
	}

	@Nullable
	public static <T> T newInstanceElse(String className, @Nullable T defaultInstance) {
		return (T) newInstance(className).orElse(defaultInstance);
	}

	@Nullable
	public static <T> T newInstanceElseGet(Class<T> type, Supplier<? extends T> defaultSupplier) {
		return newInstance(type).orElseGet(defaultSupplier);
	}

	@Nullable
	public static <T> T newInstanceElseGet(String className, Supplier<? extends T> defaultSupplier) {
		return (T) newInstance(className).orElseGet(defaultSupplier);
	}

}
