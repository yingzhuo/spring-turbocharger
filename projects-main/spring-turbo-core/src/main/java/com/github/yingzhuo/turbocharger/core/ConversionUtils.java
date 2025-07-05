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
package com.github.yingzhuo.turbocharger.core;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link ConversionService} 相关工具
 *
 * @author 应卓
 * @see SpringUtils
 * @see ConversionService
 * @since 1.0.0
 */
@Deprecated(forRemoval = true, since = "3.5.3")
public final class ConversionUtils {

	/**
	 * 私有构造方法
	 */
	private ConversionUtils() {
		super();
	}

	/**
	 * 判断是否可以完成转换
	 *
	 * @param sourceType 源类型
	 * @param targetType 目标类型
	 * @return 判断结果
	 * @throws UnsupportedOperationException 无法定位{@code ApplicationContext}实例
	 */
	public static boolean canConvert(Class<?> sourceType, Class<?> targetType) {
		Assert.notNull(sourceType, "sourceType is required");
		Assert.notNull(targetType, "targetType is required");
		return SpringUtils.getConversionService().canConvert(sourceType, targetType);
	}

	/**
	 * 判断是否可以完成转换
	 *
	 * @param sourceType 源类型
	 * @param targetType 目标类型
	 * @return 判断结果
	 * @throws UnsupportedOperationException 无法定位{@code ApplicationContext}实例
	 */
	public static boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
		Assert.notNull(sourceType, "sourceType is required");
		Assert.notNull(targetType, "targetType is required");
		return SpringUtils.getConversionService().canConvert(sourceType, targetType);
	}

	/**
	 * 转换
	 *
	 * @param source     源对象实例
	 * @param targetType 目标类型
	 * @param <T>        目标类型泛型
	 * @return 转换结果或null
	 * @throws UnsupportedOperationException 无法定位{@code ApplicationContext}实例
	 */
	@Nullable
	public static <T> T convert(Object source, Class<T> targetType) {
		Assert.notNull(source, "source is required");
		Assert.notNull(targetType, "targetType is required");
		return SpringUtils.getConversionService().convert(source, targetType);
	}

	/**
	 * 转换
	 *
	 * @param source     源对象实例
	 * @param targetType 目标类型
	 * @param <T>        目标类型泛型
	 * @return 转换结果或null
	 * @throws UnsupportedOperationException 无法定位{@code ApplicationContext}实例
	 * @throws IllegalArgumentException      无法转换
	 */
	public static <T> T convertOrThrow(Object source, Class<T> targetType) {
		final T target = convert(source, targetType);
		Assert.notNull(target, "cannot convert");
		return target;
	}

}
