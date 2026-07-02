package com.github.yingzhuo.turbocharger.util.reflection;

import org.jspecify.annotations.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

public final class ConstructorUtils {

	private ConstructorUtils() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static <T> void makeAccessible(Constructor<T> constructor) {
		ReflectionUtils.makeAccessible(constructor);
	}

	// -----------------------------------------------------------------------------------------------------------------

	@Nullable
	public static <T> Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) {
		try {
			return ReflectionUtils.accessibleConstructor(clazz, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}
