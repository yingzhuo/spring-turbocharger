/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util.spi;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see ServiceLoader
 * @see SPILoader#builder(Class)
 * @see SPILoader#builder(Class, ClassLoader)
 * @since 3.5.0
 */
public final class ServiceLoaderUtils {

	/**
	 * 私有构造方法
	 */
	private ServiceLoaderUtils() {
		super();
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType 要加载的抽象类型
	 * @param <T>        要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null, null);
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType  要加载的抽象类型
	 * @param classLoader 类加载器
	 * @param <T>         要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader) {
		return load(targetType, classLoader, null);
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType  要加载的抽象类型
	 * @param classLoader 类加载器
	 * @param filter      过滤器
	 * @param <T>         要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader, @Nullable Predicate<Class<?>> filter) {
		Assert.notNull(targetType, "targetType must not be null");

		Predicate<Class<?>> predicateToUse = filter != null ? filter : c -> true;
		return ServiceLoader.load(targetType, classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader())
			.stream()
			.filter(p -> predicateToUse.test(p.type())).map(ServiceLoader.Provider::get);
	}

}
