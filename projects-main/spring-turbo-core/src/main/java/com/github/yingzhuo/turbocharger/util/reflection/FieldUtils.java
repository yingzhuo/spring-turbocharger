package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class FieldUtils {

	private FieldUtils() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static void makeAccessible(Field field) {
		ReflectionUtils.makeAccessible(field);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static List<Field> find(Class<?> clazz) {
		final List<Field> list = new ArrayList<>();
		ReflectionUtils.doWithFields(clazz, list::add);
		return list;
	}

	public static List<Field> find(Class<?> clazz, String name) {
		final List<Field> list = new ArrayList<>();
		final Field field = ReflectionUtils.findField(clazz, name);
		if (field != null) {
			list.add(field);
		}
		return list;
	}

	public static List<Field> find(Class<?> clazz, String name, Class<?> fieldType) {
		final List<Field> list = new ArrayList<>();
		final Field field = ReflectionUtils.findField(clazz, name, fieldType);
		if (field != null) {
			list.add(field);
		}
		return list;
	}

}
