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
package com.github.yingzhuo.turbocharger.util;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 枚举类型相关工具
 *
 * @author 应卓
 * @since 1.0.2
 */
public final class EnumUtils {

	/**
	 * 私有构造方法
	 */
	private EnumUtils() {
		super();
	}

	/**
	 * 获取枚举值
	 *
	 * @param enumClass 枚举类型
	 * @param enumName  枚举字符串
	 * @param <E>       枚举类型泛型
	 * @return 枚举值
	 */
	@Nullable
	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
		return getEnum(enumClass, enumName, null);
	}

	/**
	 * 获取枚举值
	 *
	 * @param enumClass   枚举类型
	 * @param enumName    枚举字符串
	 * @param defaultEnum 默认值
	 * @param <E>         枚举类型泛型
	 * @return 枚举值
	 */
	@Nullable
	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName,
												@Nullable final E defaultEnum) {

		Assert.hasText(enumName, "enumName is required");
		try {
			return Enum.valueOf(enumClass, enumName);
		} catch (final IllegalArgumentException ex) {
			return defaultEnum;
		}
	}

	/**
	 * 获取枚举值 (忽略大小写)
	 *
	 * @param enumClass 枚举类型
	 * @param enumName  枚举字符串
	 * @param <E>       枚举类型泛型
	 * @return 枚举值
	 */
	@Nullable
	public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
		return getEnumIgnoreCase(enumClass, enumName, null);
	}

	/**
	 * 获取枚举值 (忽略大小写)
	 *
	 * @param enumClass   枚举类型
	 * @param enumName    枚举字符串
	 * @param defaultEnum 默认值
	 * @param <E>         枚举类型泛型
	 * @return 枚举值
	 */
	@Nullable
	public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass, final String enumName,
														  @Nullable final E defaultEnum) {

		Assert.notNull(enumClass, "enumClass is required");
		Assert.hasText(enumName, "enumName is required");

		for (final E each : enumClass.getEnumConstants()) {
			if (each.name().equalsIgnoreCase(enumName)) {
				return each;
			}
		}
		return defaultEnum;
	}

	/**
	 * 获取所有的枚举值
	 *
	 * @param enumClass 枚举类型
	 * @param <E>       枚举类型泛型
	 * @return 所有的枚举值
	 */
	public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
		return new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
	}

	/**
	 * 获取所有的枚举值
	 *
	 * @param enumClass 枚举类型
	 * @param <E>       枚举类型泛型
	 * @return 所有的枚举值
	 */
	public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
		final Map<String, E> map = new LinkedHashMap<>();
		for (final E e : enumClass.getEnumConstants()) {
			map.put(e.name(), e);
		}
		return map;
	}

	/**
	 * 判断字符串是否为合法的枚举值
	 *
	 * @param enumClass 枚举类型
	 * @param enumName  枚举字符串
	 * @param <E>       枚举类泛型
	 * @return 合法时返回 {@code true} 否则返回 {@code false}
	 */
	public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
		return getEnum(enumClass, enumName) != null;
	}

	/**
	 * 判断字符串是否为合法的枚举值 (忽略大小写)
	 *
	 * @param enumClass 枚举类型
	 * @param enumName  枚举字符串
	 * @param <E>       枚举类泛型
	 * @return 合法时返回 {@code true} 否则返回 {@code false}
	 */
	public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(final Class<E> enumClass, final String enumName) {
		return getEnumIgnoreCase(enumClass, enumName) != null;
	}

}
