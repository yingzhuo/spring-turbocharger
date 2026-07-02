package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MethodUtils {

	private MethodUtils() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static void makeAccessible(Method method) {
		ReflectionUtils.makeAccessible(method);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static List<Method> find(Class<?> clazz) {
		var list = new ArrayList<Method>();
		ReflectionUtils.doWithMethods(clazz, list::add);
		return list;
	}

	public static List<Method> find(Class<?> clazz, String name) {
		var list = new ArrayList<Method>();
		Optional.ofNullable(ReflectionUtils.findMethod(clazz, name))
			.ifPresent(list::add);
		return list;
	}

	public static List<Method> find(Class<?> clazz, String name, Class<?>... paramTypes) {
		var list = new ArrayList<Method>();
		Optional.ofNullable(ReflectionUtils.findMethod(clazz, name, paramTypes)).ifPresent(list::add);
		return list;
	}

}
