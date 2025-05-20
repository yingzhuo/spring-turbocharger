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

import com.github.yingzhuo.turbocharger.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * {@link ServiceLoader}相关工具
 *
 * @author 应卓
 * @see java.util.ServiceLoader
 * @see org.springframework.core.Ordered
 * @see SpringFactoriesUtils
 * @since 1.0.0
 */
public final class ServiceLoaderUtils {

	private static final Logger log = LoggerFactory.getLogger(ServiceLoaderUtils.class);

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
	public static <T> List<T> load(Class<T> targetType) {
		return load(targetType, null);
	}

	/**
	 * 加载Service
	 *
	 * @param targetType  Service类型
	 * @param classLoader ClassLoader实例
	 * @param <T>         Service类型泛型
	 * @return Service实例
	 * @throws ServiceConfigurationError 加载失败时抛出此错误
	 */
	public static <T> List<T> load(Class<T> targetType, @Nullable ClassLoader classLoader) {
		Assert.notNull(targetType, "targetType is required");

		try {
			classLoader = Objects.requireNonNullElseGet(classLoader, ClassUtils::getDefaultClassLoader);
			final ServiceLoader<T> loader = ServiceLoader.load(targetType, classLoader);
			List<T> list = new LinkedList<>();
			for (T it : loader) {
				list.add(it);
			}
			OrderComparator.sort(list);
			return Collections.unmodifiableList(list);
		} catch (ServiceConfigurationError error) {
			// 记录日志，但不吞此异常
			log.warn(error.getMessage(), error);
			throw error;
		}
	}

	/**
	 * 加载Service
	 *
	 * @param targetType Service类型
	 * @param <T>        Service类型泛型
	 * @return Service实例
	 */
	public static <T> List<T> loadQuietly(Class<T> targetType) {
		return loadQuietly(targetType, null);
	}

	/**
	 * 加载Service
	 *
	 * @param targetType  Service类型
	 * @param classLoader ClassLoader实例
	 * @param <T>         Service类型泛型
	 * @return Service实例
	 */
	public static <T> List<T> loadQuietly(Class<T> targetType, @Nullable ClassLoader classLoader) {
		try {
			return load(targetType, classLoader);
		} catch (ServiceConfigurationError e) {
			return Collections.emptyList();
		}
	}

}
