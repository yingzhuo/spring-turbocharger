/*
 * Copyright 2025-present the original author or authors.
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 反射工具 - Method
 *
 * @author 应卓
 * @see MethodPredicateFactories
 * @see ConstructorUtils
 * @see FieldUtils
 * @since 1.2.1
 */
public final class MethodUtils {

	/**
	 * 私有构造方法
	 */
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
