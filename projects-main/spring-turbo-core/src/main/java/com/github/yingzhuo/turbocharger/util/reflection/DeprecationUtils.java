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
package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * 代码过期性检查工具
 *
 * @author 应卓
 * @since 1.0.4
 */
public final class DeprecationUtils {

	/**
	 * {@link Deprecated}类型
	 */
	private static final Class<Deprecated> ANNOTATION_TYPE = Deprecated.class;

	/**
	 * 私有构造方法
	 */
	private DeprecationUtils() {
	}

	/**
	 * 检查{@code AnnotatedElement}实例是否已经过期
	 *
	 * @param annotationSupplier {code AnnotatedElement}实例
	 * @return 已过期时返回 {@code true} 否则返回 {@code false}
	 * @see java.lang.reflect.Constructor
	 * @see java.lang.reflect.Field
	 * @see java.lang.reflect.Parameter
	 */
	public boolean isDeprecated(AnnotatedElement annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

	/**
	 * 检查方法是否已经过期
	 *
	 * @param annotationSupplier 方法
	 * @return 已过期时返回 {@code true} 否则返回 {@code false}
	 */
	public boolean isDeprecated(Method annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

	/**
	 * 检查类型是否已经过期
	 *
	 * @param annotationSupplier 类型
	 * @return 已过期时返回 {@code true} 否则返回 {@code false}
	 */
	public boolean isDeprecated(Class<?> annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

}
