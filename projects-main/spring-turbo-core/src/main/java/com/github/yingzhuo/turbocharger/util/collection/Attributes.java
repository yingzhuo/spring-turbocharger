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
package com.github.yingzhuo.turbocharger.util.collection;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Attributes
 *
 * @author 应卓
 * @see MultiValueMap
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class Attributes extends LinkedMultiValueMap<String, Object> implements MultiValueMap<String, Object> {

	/**
	 * 构造方法
	 */
	public Attributes() {
		super();
	}

	/**
	 * 构造方法
	 *
	 * @param map 其他数据来源
	 */
	public Attributes(@Nullable Map<String, List<Object>> map) {
		if (map != null && !map.isEmpty()) {
			super.putAll(map);
		}
	}

	/**
	 * 构造方法
	 *
	 * @param anotherAttributes 其他数据来源
	 */
	public Attributes(@Nullable Attributes anotherAttributes) {
		this((Map<String, List<Object>>) anotherAttributes);
	}

	/**
	 * 创建Attributes实例
	 *
	 * @return Attributes实例
	 */
	public static Attributes newInstance() {
		return new Attributes();
	}

	/**
	 * 通过 {@link Map} 创建Attributes实例
	 *
	 * @param map map
	 * @return Attributes实例
	 * @since 1.0.1
	 */
	public static Attributes fromMap(@Nullable Map<String, Object> map) {
		final Attributes attributes = new Attributes();
		if (!CollectionUtils.isEmpty(map)) {
			for (String key : map.keySet()) {
				Object value = map.get(key);
				attributes.add(key, value);
			}
		}
		return attributes;
	}

	/**
	 * 从字典构建对象
	 *
	 * @param map 其他数据来源
	 * @return Attributes实例
	 * @since 1.1.2
	 */
	public static Attributes fromListMap(@Nullable Map<String, List<Object>> map) {
		return new Attributes(map);
	}

	/**
	 * 从其他attributes构建本对象
	 *
	 * @param attributes 其他数据来源
	 * @return Attributes实例
	 * @since 1.1.2
	 */
	public static Attributes fromAttributes(@Nullable Attributes attributes) {
		return new Attributes(attributes);
	}

	/**
	 * 通过 {@link MultiValueMap} 的实例创建Attributes实例
	 *
	 * @param map map
	 * @return Attributes实例
	 * @since 1.0.1
	 */
	public static Attributes fromMultiValueMap(@Nullable MultiValueMap<String, Object> map) {
		final Attributes attributes = new Attributes();
		Optional.ofNullable(map).ifPresent(attributes::addAll);
		return attributes;
	}

	/**
	 * 获取第一个值
	 *
	 * @param key key
	 * @param <T> 返回值类型泛型
	 * @return 值或者 {@code null}
	 * @since 1.0.1
	 */
	@Nullable
	public <T> T findFirst(String key) {
		Assert.notNull(key, "key is required");
		return (T) super.getFirst(key);
	}

	/**
	 * 获取第一个值
	 *
	 * @param key           key
	 * @param defaultIfNull 找不到时的默认值
	 * @param <T>           返回值类型泛型
	 * @return 值或者默认值
	 * @see #findFirst(String)
	 * @see #findRequiredFirst(String)
	 * @see #findRequiredFirst(String, Supplier)
	 * @since 1.0.1
	 */
	@Nullable
	public <T> T findFirstOrDefault(String key, @Nullable T defaultIfNull) {
		Assert.notNull(key, "key is required");
		return Optional.<T>ofNullable(findFirst(key)).orElse(defaultIfNull);
	}

	/**
	 * 获取第一个值
	 *
	 * @param key           key
	 * @param defaultIfNull 找不到时的默认值生成器
	 * @param <T>           返回值类型泛型
	 * @return 值或者默认值
	 * @see #findFirst(String)
	 * @see #findRequiredFirst(String)
	 * @see #findRequiredFirst(String, Supplier)
	 * @since 1.1.2
	 */
	@Nullable
	public <T> T findFirstOrDefault(String key, Supplier<T> defaultIfNull) {
		Assert.notNull(key, "key is required");
		return Optional.<T>ofNullable(findFirst(key)).orElseGet(defaultIfNull);
	}

	/**
	 * 获取第一个值，如果找不到key值将抛出异常
	 *
	 * @param key key
	 * @param <T> 返回值类型泛型
	 * @return 值
	 * @throws NoSuchElementException 找不到key值时抛出异常
	 * @see #findRequiredFirst(String)
	 * @since 1.0.5
	 */
	public <T> T findRequiredFirst(String key) {
		return findRequiredFirst(key,
			() -> new NoSuchElementException(StringFormatter.format("element not found. key: {}", key)));
	}

	/**
	 * 获取第一个值，如果找不到key值将抛出异常
	 *
	 * @param key                    key
	 * @param exceptionIfKeyNotFound 找不到key对应的值时的异常提供器
	 * @param <T>                    返回值类型泛型
	 * @return 值
	 * @see #findRequiredFirst(String)
	 * @since 1.0.5
	 */
	public <T> T findRequiredFirst(String key,
								   Supplier<? extends RuntimeException> exceptionIfKeyNotFound) {
		Assert.notNull(key, "key is required");
		Assert.notNull(exceptionIfKeyNotFound, "exceptionIfKeyNotFound is required");

		T obj = findFirst(key);
		if (obj == null) {
			throw exceptionIfKeyNotFound.get();
		}
		return obj;
	}

}
