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

import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * {@link ServiceLoader}相关工具
 *
 * @author 应卓
 * @see java.util.ServiceLoader
 * @see org.springframework.core.Ordered
 * @since 1.0.0
 */
@Deprecated(forRemoval = true, since = "3.5.0")
public final class ServiceLoaderUtils {

	/**
	 * 私有构造方法
	 */
	private ServiceLoaderUtils() {
		super();
	}

	/**
	 * 加载Service
	 *
	 * @param targetType Service类型
	 * @param <T>        Service类型泛型
	 * @return Service实例
	 * @throws ServiceConfigurationError 加载失败时抛出此错误
	 */
	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null);
	}

	/**
	 * 加载Service
	 *
	 * @param targetType Service类型
	 * @param filter     类型过滤器
	 * @param <T>        Service类型泛型
	 * @return Service实例
	 * @throws ServiceConfigurationError 加载失败时抛出此错误
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable Predicate<ServiceLoader.Provider<T>> filter) {
		Assert.notNull(targetType, "targetType is required");

		return ServiceLoader.load(targetType)
			.stream()
			.filter(Objects.requireNonNullElse(filter, c -> true))
			.map(ServiceLoader.Provider::get)
			.sorted(OrderComparator.INSTANCE);
	}

	private record ByTypePredicate<T>(Predicate<Class<?>> filter) implements Predicate<ServiceLoader.Provider<T>> {
		@Override
		public boolean test(ServiceLoader.Provider<T> provider) {
			return filter.test(provider.type());
		}
	}

}
