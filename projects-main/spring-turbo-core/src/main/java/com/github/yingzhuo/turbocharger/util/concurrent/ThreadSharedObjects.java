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
package com.github.yingzhuo.turbocharger.util.concurrent;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 简易工具，在同一线程中存取对象
 *
 * @author 应卓
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
@Deprecated(since = "3.5.3")
public final class ThreadSharedObjects {

	/**
	 * 按类型保存对象
	 */
	private static final ThreadLocal<Map<Class<?>, Object>> TYPE_HOLDER = ThreadLocal.withInitial(HashMap::new);

	/**
	 * 按名称保存对象
	 */
	private static final ThreadLocal<Map<String, Object>> NAME_HOLDER = ThreadLocal.withInitial(HashMap::new);

	/**
	 * 私有构造方法
	 */
	private ThreadSharedObjects() {
	}

	/**
	 * 保存数据
	 *
	 * @param type 数据类型
	 * @param obj  数据
	 * @param <T>  数据类型泛型
	 */
	public static <T> void put(Class<T> type, T obj) {
		TYPE_HOLDER.get().put(type, obj);
	}

	/**
	 * 保存数据
	 *
	 * @param name 数据名称
	 * @param obj  数据
	 */
	public static void put(String name, Object obj) {
		NAME_HOLDER.get().put(name, obj);
	}

	/**
	 * 按类型拿取数据
	 *
	 * @param type 数据类型
	 * @param <T>  数据类型泛型
	 * @return 数据或 {@code null}
	 */
	@Nullable
	public static <T> T get(Class<T> type) {
		return (T) TYPE_HOLDER.get().get(type);
	}

	/**
	 * 按名称拿取数据
	 *
	 * @param name 数据名称
	 * @param <T>  数据类型泛型
	 * @return 数据或 {@code null}
	 */
	@Nullable
	public static <T> T get(String name) {
		return (T) NAME_HOLDER.get().get(name);
	}

	/**
	 * 按类型拿取数据，并断言数据一定不是空值
	 *
	 * @param type 数据类型
	 * @param <T>  数据类型泛型
	 * @return 数据
	 */
	public static <T> T required(Class<T> type) {
		var o = get(type);
		if (o == null) {
			var msg = StringFormatter.format("Cannot get object of type: '{}'", type.getName());
			throw new IllegalStateException(msg);
		}
		return o;
	}

	/**
	 * 按名称拿取数据，并断言数据一定不是空值
	 *
	 * @param name 数据名称
	 * @param <T>  数据类型泛型
	 * @return 数据
	 */
	public static <T> T required(String name) {
		var o = get(name);
		if (o == null) {
			var msg = StringFormatter.format("Cannot get object of name: '{}'", name);
			throw new IllegalStateException(msg);
		}
		return (T) o;
	}

	/**
	 * 清除所有已保存的数据
	 *
	 * @see ThreadLocal#remove()
	 */
	public static void remove() {
		TYPE_HOLDER.remove();
		TYPE_HOLDER.set(new HashMap<>());   // 一个空集合也浪费不了多少内存，算了
		NAME_HOLDER.remove();
		NAME_HOLDER.set(new HashMap<>());
	}

}
