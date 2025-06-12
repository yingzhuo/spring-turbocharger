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
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;
import static org.springframework.core.io.support.SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION;

/**
 * @param <T> 要加载的抽象类型
 * @author 应卓
 * @see SpringFactoriesLoader
 * @since 3.5.0
 */
public final class SPILoaderBuilder<T> {

	private final Class<T> targetType;
	private final ClassLoader classLoader;

	@Nullable
	private SpringFactoriesConfig springFactoriesConfig = null;

	private boolean jdkServiceLoaderEnabled = false;

	@NonNull
	private Predicate<Class<?>> filter = c -> true;

	@Nullable
	private Comparator<? super T> comparator;

	SPILoaderBuilder(Class<T> targetType, @Nullable ClassLoader classLoader) {
		Assert.notNull(targetType, "targetType must not be null");

		this.targetType = targetType;
		this.classLoader = requireNonNullElseGet(classLoader, ClassUtils::getDefaultClassLoader);
	}

	public SPILoaderBuilder<T> filter(@Nullable Predicate<Class<?>> filter) {
		this.filter = requireNonNullElse(filter, c -> true);
		return this;
	}

	public SPILoaderBuilder<T> withSpringFactories() {
		return withSpringFactories(FACTORIES_RESOURCE_LOCATION);
	}

	public SPILoaderBuilder<T> withSpringFactories(@Nullable String resourceLocation) {
		this.springFactoriesConfig = new SpringFactoriesConfig(requireNonNullElse(resourceLocation, FACTORIES_RESOURCE_LOCATION));
		return this;
	}

	public SPILoaderBuilder<T> withJdkServiceLoader() {
		this.jdkServiceLoaderEnabled = true;
		return this;
	}

	public SPILoaderBuilder<T> orderComparator(Comparator<? super T> comparator) {
		Assert.notNull(comparator, "comparator must not be null");
		this.comparator = comparator;
		return this;
	}

	public SPILoader<T> build() {
		return new SPILoaderImpl<>(
			this.targetType,
			this.classLoader,
			this.springFactoriesConfig,
			this.jdkServiceLoaderEnabled,
			this.filter,
			this.comparator
		);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public record SpringFactoriesConfig(String resourceLocation) {
	}

	// -----------------------------------------------------------------------------------------------------------------


	private record SPILoaderImpl<T>(
		@NonNull Class<T> targetType,
		@NonNull ClassLoader classLoader,
		@Nullable SpringFactoriesConfig springFactoriesConfig,
		boolean jdkServiceLoaderEnabled,
		@NonNull Predicate<Class<?>> filter,
		@Nullable Comparator<? super T> comparator) implements SPILoader<T> {


		@Override
		public Stream<T> load() {
			Stream<T> result = Stream.empty();

			if (jdkServiceLoaderEnabled) {
				var s1 = ServiceLoader.load(targetType, classLoader)
					.stream()
					.filter(provider -> filter.test(provider.type()))
					.map(ServiceLoader.Provider::get);

				result = Stream.concat(result, s1);
			}

			if (springFactoriesConfig != null) {
				var s2 = SpringFactoriesLoader.forResourceLocation(
						springFactoriesConfig.resourceLocation,
						classLoader
					).load(targetType)
					.stream()
					.filter(new Predicate<T>() {
						@Override
						public boolean test(T t) {
							return filter.test(t.getClass());
						}
					});

				result = Stream.concat(result, s2);
			}

			return comparator != null ? result.sorted(comparator) : result;
		}
	}
}
