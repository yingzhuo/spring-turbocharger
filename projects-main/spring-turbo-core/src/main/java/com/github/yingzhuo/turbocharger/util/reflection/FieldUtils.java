/*
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具 - Field
 *
 * @author 应卓
 * @see FieldPredicateFactories
 * @see ConstructorUtils
 * @see MethodUtils
 * @since 1.2.1
 */
public final class FieldUtils {

	/**
	 * 私有构造方法
	 */
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
