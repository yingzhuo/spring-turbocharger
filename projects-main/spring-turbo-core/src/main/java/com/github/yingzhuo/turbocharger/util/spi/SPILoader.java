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

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Stream;

/**
 * SPI加载工具
 *
 * @param <T> 要加载的抽象类型
 * @author 应卓
 * @see java.util.ServiceLoader
 * @see org.springframework.core.io.support.SpringFactoriesLoader
 * @see #builder(Class)
 * @see #builder(Class, ClassLoader)
 * @since 3.5.0
 */
@FunctionalInterface
public interface SPILoader<T> {

	public static <T> SPILoaderBuilder<T> builder(Class<T> targetType) {
		return builder(targetType, null);
	}

	public static <T> SPILoaderBuilder<T> builder(Class<T> targetType, @Nullable ClassLoader classLoader) {
		return new SPILoaderBuilder<>(targetType, classLoader);
	}

	public Stream<T> load();

	public default List<T> loadAsList() {
		return load().toList();
	}

}
