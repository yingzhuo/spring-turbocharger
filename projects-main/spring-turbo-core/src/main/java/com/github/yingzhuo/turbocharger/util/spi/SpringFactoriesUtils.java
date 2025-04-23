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
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.yingzhuo.turbocharger.util.StringDefaults.blankToDefault;
import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;
import static java.util.Objects.requireNonNullElse;
import static org.springframework.core.io.support.SpringFactoriesLoader.forResourceLocation;
import static org.springframework.util.ClassUtils.getDefaultClassLoader;

/**
 * {@link SpringFactoriesLoader} 相关工具
 *
 * @author 应卓
 * @see SpringFactoriesLoader
 * @see ServiceLoaderUtils
 * @since 2.0.5
 */
public final class SpringFactoriesUtils {

	private static final SpringFactoriesLoader.FailureHandler NOOP = (factoryType, factoryImplementationName, failure) -> {
	};

	/**
	 * 私有构造方法
	 */
	private SpringFactoriesUtils() {
	}

	/**
	 * 获取实现
	 *
	 * @param factoryType 实现的类型
	 * @param <T>         实现的接口类型
	 * @return 实例
	 */
	public static <T> List<T> loadQuietly(Class<T> factoryType) {
		return loadQuietly(factoryType, null);
	}

	/**
	 * 获取实现
	 *
	 * @param factoryType               实现的类型
	 * @param factoriesResourceLocation 配置文件位置
	 * @param <T>                       实现的接口类型
	 * @return 实例
	 */
	public static <T> List<T> loadQuietly(Class<T> factoryType,
										  @Nullable String factoriesResourceLocation) {

		return loadQuietly(factoryType, factoriesResourceLocation, null);
	}

	/**
	 * 获取实现
	 *
	 * @param factoryType               实现的类型
	 * @param factoriesResourceLocation 配置文件位置
	 * @param classLoader               类型加载器
	 * @param <T>                       实现的接口类型
	 * @return 实例
	 */
	public static <T> List<T> loadQuietly(Class<T> factoryType,
										  @Nullable String factoriesResourceLocation,
										  @Nullable ClassLoader classLoader) {

		Assert.notNull(factoryType, "factoryType is required");

		factoriesResourceLocation = blankToDefault(factoriesResourceLocation, SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION);
		classLoader = requireNonNullElse(classLoader, getDefaultClassLoader());

		var factoriesLoader = forResourceLocation(factoriesResourceLocation, classLoader);

		try {
			final List<T> list = new ArrayList<>();
			var services = factoriesLoader.load(factoryType, NOOP);
			nullSafeAddAll(list, services);
			OrderComparator.sort(list);
			return Collections.unmodifiableList(list);
		} catch (Throwable e) {
			return List.of();
		}
	}

}
