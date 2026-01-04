/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.jackson.util;

import com.jayway.jsonpath.TypeRef;

import java.lang.reflect.Type;

/**
 * 简单{@link TypeRef}实现
 *
 * @param <T> 泛型类型
 * @author 应卓
 * @since 3.3.1
 */
public final class SimpleTypeRef<T> extends TypeRef<T> {

	private final Class<T> clz;

	public SimpleTypeRef(Class<T> clz) {
		this.clz = clz;
	}

	public static <T> TypeRef<T> of(Class<T> clazz) {
		return new SimpleTypeRef<>(clazz);
	}

	@Override
	public Type getType() {
		return this.clz;
	}

}
