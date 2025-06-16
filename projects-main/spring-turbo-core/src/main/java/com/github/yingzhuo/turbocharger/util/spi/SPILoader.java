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
package com.github.yingzhuo.turbocharger.util.spi;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * SPI加载工具
 *
 * @param <T> 要加载的抽象类型
 * @author 应卓
 * @see ServiceLoader
 * @see SpringFactoriesLoader
 * @see Builder
 * @see #builder(Class)
 * @see #builder(Class, ClassLoader)
 * @since 3.5.0
 */
public sealed interface SPILoader<T> extends Supplier<Stream<T>> permits SPILoader.Default {

	/**
	 * 获取默认的加载器
	 *
	 * @param targetType 要加载的抽象类型
	 * @return 加载的实例
	 */
	public static <T> SPILoader<T> getDefault(Class<T> targetType) {
		return getDefault(targetType, c -> true);
	}

	/**
	 * 获取默认的加载器
	 *
	 * @param targetType 要加载的抽象类型
	 * @param filter     过滤器
	 * @param <T>        要加载的抽象类型
	 * @return 加载的实例
	 */
	public static <T> SPILoader<T> getDefault(Class<T> targetType, @Nullable Predicate<Class<?>> filter) {
		return builder(targetType)
			.filter(filter != null ? filter : c -> true)
			.withSpringFactories()
			.withJdkServiceLoader()
			.build();
	}

	/**
	 * 生成创建器
	 *
	 * @param targetType 要加载的抽象类型
	 * @param <T>        要加载的抽象类型
	 * @return 创建器实例
	 */
	public static <T> Builder<T> builder(Class<T> targetType) {
		return builder(targetType, null);
	}

	/**
	 * 生成创建器
	 *
	 * @param targetType  要加载的抽象类型
	 * @param classLoader 类型加载器
	 * @param <T>         要加载的抽象类型
	 * @return 创建器实例
	 */
	public static <T> Builder<T> builder(Class<T> targetType, @Nullable ClassLoader classLoader) {
		return new Builder<>(targetType, classLoader);
	}

	/**
	 * 加载SPI实例
	 *
	 * @return SPI实例
	 */
	public Stream<T> load();

	/**
	 * 加载SPI实例
	 *
	 * @return SPI实例
	 */
	public default List<T> loadAsList() {
		return load().toList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public default Stream<T> get() {
		return load();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public final class Builder<T> {

		private final Class<T> targetType;
		private final ClassLoader classLoader;

		@Nullable
		private String springFactoriesResourceLocation;

		private boolean jdkServiceLoaderEnabled = false;

		@NonNull
		private Predicate<Class<?>> filter = c -> true;

		@Nullable
		private Comparator<? super T> comparator;

		Builder(Class<T> targetType, @Nullable ClassLoader classLoader) {
			Assert.notNull(targetType, "targetType must not be null");
			this.targetType = targetType;
			this.classLoader = Objects.requireNonNullElse(classLoader, ClassUtils.getDefaultClassLoader());
		}

		public Builder<T> filter(Predicate<Class<?>> filter) {
			Assert.notNull(filter, "filter must not be null");
			this.filter = filter;
			return this;
		}

		public Builder<T> filterByClassName(String regex) {
			Assert.notNull(regex, "regex must not be null");
			return filter(c -> c.getName().matches(regex));
		}

		public Builder<T> withSpringFactories() {
			return withSpringFactories(SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION);
		}

		public Builder<T> withSpringFactories(String resourceLocation) {
			Assert.notNull(resourceLocation, "resourceLocation must not be null");
			this.springFactoriesResourceLocation = resourceLocation;
			return this;
		}

		public Builder<T> withJdkServiceLoader() {
			this.jdkServiceLoaderEnabled = true;
			return this;
		}

		public Builder<T> orderComparator(Comparator<? super T> comparator) {
			Assert.notNull(comparator, "comparator must not be null");
			this.comparator = comparator;
			return this;
		}

		public SPILoader<T> build() {
			return new Default<>(
				this.targetType,
				this.classLoader,
				this.springFactoriesResourceLocation,
				this.jdkServiceLoaderEnabled,
				this.filter,
				this.comparator
			);
		}
	}

	// @formatter:off
	record Default<T>(
		@NonNull Class<T> targetType,
		@NonNull ClassLoader classLoader,
		@Nullable String springFactoriesResourceLocation,
		boolean jdkServiceLoaderEnabled,
		@NonNull Predicate<Class<?>> filter,
		@Nullable Comparator<? super T> comparator) implements SPILoader<T> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Stream<T> load() {
			Stream<T> result = Stream.empty();

			if (jdkServiceLoaderEnabled) {
				result = Stream.concat(result, ServiceLoaderUtils.load(targetType, classLoader, filter));
			}

			if (springFactoriesResourceLocation != null) {
				result = Stream.concat(result, SpringFactoriesUtils.load(targetType, springFactoriesResourceLocation, classLoader, filter));
			}

			return comparator != null ? result.sorted(comparator) : result;
		}
	}
	// @formatter:on

}
