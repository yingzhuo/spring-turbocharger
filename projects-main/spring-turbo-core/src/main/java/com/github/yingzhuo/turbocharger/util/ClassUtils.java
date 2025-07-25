/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util;

import com.github.yingzhuo.turbocharger.util.reflection.InstanceUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * {@link Class} 相关工具
 *
 * @author 应卓
 * @see InstanceUtils
 * @since 1.0.2
 */
public final class ClassUtils {

	@Nullable
	private static final ClassLoader DEFAULT_CLASSLOADER = org.springframework.util.ClassUtils.getDefaultClassLoader();

	/**
	 * 私有构造方法
	 */
	private ClassUtils() {
		super();
	}

	/**
	 * 获取默认的{@link ClassLoader}
	 *
	 * @return {@link ClassLoader}实例
	 */
	public static ClassLoader getDefaultClassLoader() {
		// (only null if even the system ClassLoader isn't accessible)
		return Optional.ofNullable(DEFAULT_CLASSLOADER)
			.orElse(Thread.currentThread().getContextClassLoader());
	}

	/**
	 * 尝试加载类型或抛出异常
	 *
	 * @param className 类型全名，不可为 {@code null}
	 * @return 加载结果
	 * @see #forName(String)
	 * @see #forNameElseThrow(String, Supplier)
	 */
	public static Class<?> forNameElseThrow(@NonNull String className) {
		return forNameElseThrow(className, new ClassLoadingExceptionSupplier(className));
	}

	/**
	 * 尝试加载类型或抛出异常
	 *
	 * @param className             类型全名，不可为 {@code null}
	 * @param exceptionIfCannotLoad 异常提供者，不可为 {@code null}
	 * @return 加载结果
	 */
	public static Class<?> forNameElseThrow(@NonNull String className,
											@NonNull Supplier<? extends RuntimeException> exceptionIfCannotLoad) {
		Assert.notNull(exceptionIfCannotLoad, "exceptionIfCannotLoad is required");
		return forName(className).orElseThrow(exceptionIfCannotLoad);
	}

	/**
	 * 尝试加载类型
	 * <p>
	 * 例子:
	 * <ul>
	 * <li>{@code ClassUtils.forName("int")}</li>
	 * <li>{@code ClassUtils.forName("int[]")}</li>
	 * <li>{@code ClassUtils.forName("[[Ljava.lang.String;")}</li>
	 * <li>{@code ClassUtils.forName("foo.Bar")}</li>
	 * <li>{@code ClassUtils.forName("foo.Bar[]")}</li>
	 * <li>{@code ClassUtils.forName("foo.Bar.InnerClass")}</li>
	 * <li>{@code ClassUtils.forName("foo.Bar$InnerClass")}</li>
	 * </ul>
	 *
	 * @param className 类型全名
	 * @return 加载结果
	 * @see #forNameElseThrow(String)
	 * @see #forNameElseThrow(String, Supplier)
	 */
	public static Optional<Class<?>> forName(String className) {
		try {
			Assert.hasText(className, "className is required");
			final Class<?> clazz = org.springframework.util.ClassUtils.forName(className, DEFAULT_CLASSLOADER);
			return Optional.of(clazz);
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	/**
	 * 判断类型是否存在
	 *
	 * @param className 类型全名
	 * @return 存在时返回true，否则返回false
	 */
	public static boolean isPresent(String className) {
		try {
			Assert.hasText(className, "className is required");
			return org.springframework.util.ClassUtils.isPresent(className, DEFAULT_CLASSLOADER);
		} catch (IllegalAccessError e) {
			// 典型情况，该类型的依赖有缺失
			return false;
		}
	}

	/**
	 * 判断类型是否不存在
	 *
	 * @param className 类型全名
	 * @return 存在时返回false，否则返回true
	 */
	public static boolean isAbsent(String className) {
		return !isPresent(className);
	}

	/**
	 * 获取包名
	 *
	 * @param clz 类型
	 * @return 包名
	 */
	public static String getPackageName(Class<?> clz) {
		return org.springframework.util.ClassUtils.getPackageName(clz);
	}

	/**
	 * 获取包名
	 *
	 * @param className 类型
	 * @return 包名
	 */
	public static String getPackageName(String className) {
		return org.springframework.util.ClassUtils.getPackageName(className);
	}

}
