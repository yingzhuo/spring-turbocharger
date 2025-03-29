/*
 *
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
 *
 */
package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

/**
 * 反射工具 - 构造方法
 *
 * @author 应卓
 * @see MethodUtils
 * @see FieldUtils
 * @since 1.2.1
 */
public final class ConstructorUtils {

	/**
	 * 私有构造方法
	 */
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
