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
package com.github.yingzhuo.turbocharger.core;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 元注释相关工具
 *
 * @author 应卓
 * @see AnnotationUtils
 * @see AnnotationAttributes
 * @since 2.0.11
 */
public final class AnnotationHelper {

	/**
	 * 私有构造方法
	 */
	private AnnotationHelper() {
		super();
	}

	/**
	 * 查找元注释
	 *
	 * @param type           类型
	 * @param annotationType 元注释class
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例，没有查找到时返回 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A findAnnotation(Class<?> type, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(type, annotationType);
	}

	/**
	 * 查找元注释，并断言能够找到该元注释
	 *
	 * @param type           类型
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例
	 */
	public static <A extends Annotation> A findRequiredAnnotation(Class<?> type, Class<A> annotationType) {
		var annotation = findAnnotation(type, annotationType);
		return Objects.requireNonNull(annotation);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param type           类型
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Class<?> type,
		Class<A> annotationType) {
		return findAnnotationAttributes(type, annotationType, false, false);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param type                   类型
	 * @param annotationType         要查找的元注释类型
	 * @param classValueAsString     是否把元注释中的类型要素用字符串的形式表达
	 * @param nestedAnnotationsAsMap 是否把元注释中的元素要素用字符串的形式表达
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Class<?> type,
		Class<A> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {

		var annotation = findAnnotation(type, annotationType);
		if (annotation == null) {
			return new AnnotationAttributes();
		}
		return AnnotationAttributes.fromMap(
			AnnotationUtils.getAnnotationAttributes(annotation, classValueAsString, nestedAnnotationsAsMap));
	}

	/**
	 * 查找元注释
	 *
	 * @param method         方法
	 * @param annotationType 元注释class
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例，没有查找到时返回 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(method, annotationType);
	}

	/**
	 * 查找元注释，并断言能够找到该元注释
	 *
	 * @param method         方法
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例
	 */
	public static <A extends Annotation> A findRequiredAnnotation(Method method, Class<A> annotationType) {
		var annotation = AnnotationUtils.findAnnotation(method, annotationType);
		return Objects.requireNonNull(annotation);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param method         方法
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Method method,
		Class<A> annotationType) {

		return findAnnotationAttributes(method, annotationType, false, false);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param method                 方法
	 * @param annotationType         要查找的元注释类型
	 * @param classValueAsString     是否把元注释中的类型要素用字符串的形式表达
	 * @param nestedAnnotationsAsMap 是否把元注释中的元素要素用字符串的形式表达
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Method method,
		Class<A> annotationType, boolean classValueAsString, boolean nestedAnnotationsAsMap) {
		var annotation = findAnnotation(method, annotationType);
		if (annotation == null) {
			return new AnnotationAttributes();
		}
		return AnnotationAttributes.fromMap(
			AnnotationUtils.getAnnotationAttributes(annotation, classValueAsString, nestedAnnotationsAsMap));
	}

	/**
	 * 查找元注释
	 *
	 * @param field          字段
	 * @param annotationType 元注释class
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例，没有查找到时返回 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A findAnnotation(Field field, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(field, annotationType);
	}

	/**
	 * 查找元注释，并断言能够找到该元注释
	 *
	 * @param field          字段
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例
	 */
	public static <A extends Annotation> A findRequiredAnnotation(Field field, Class<A> annotationType) {
		var annotation = AnnotationUtils.findAnnotation(field, annotationType);
		return Objects.requireNonNull(annotation);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param field          字段
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Field field,
		Class<A> annotationType) {
		return findAnnotationAttributes(field, annotationType, false, false);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param field                  字段
	 * @param annotationType         要查找的元注释类型
	 * @param classValueAsString     是否把元注释中的类型要素用字符串的形式表达
	 * @param nestedAnnotationsAsMap 是否把元注释中的元素要素用字符串的形式表达
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Field field,
		Class<A> annotationType, boolean classValueAsString, boolean nestedAnnotationsAsMap) {
		var annotation = findAnnotation(field, annotationType);
		if (annotation == null) {
			return new AnnotationAttributes();
		}
		return AnnotationAttributes.fromMap(
			AnnotationUtils.getAnnotationAttributes(annotation, classValueAsString, nestedAnnotationsAsMap));
	}

	/**
	 * 查找元注释
	 *
	 * @param constructor    构造方法z
	 * @param annotationType 元注释class
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例，没有查找到时返回 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A findAnnotation(Constructor<?> constructor, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(constructor, annotationType);
	}

	/**
	 * 查找元注释，并断言能够找到该元注释
	 *
	 * @param constructor    构造方法
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例
	 */
	public static <A extends Annotation> A findRequiredAnnotation(Constructor<?> constructor, Class<A> annotationType) {
		var annotation = findAnnotation(constructor, annotationType);
		return Objects.requireNonNull(annotation);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param constructor    构造方法
	 * @param annotationType 要查找的元注释类型
	 * @param <A>            元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Constructor<?> constructor,
		Class<A> annotationType) {

		return findAnnotationAttributes(constructor, annotationType, false, false);
	}

	/**
	 * 查找元注释，并转换成 {@link AnnotationAttributes}实例 <br>
	 * 如果找不到该元注释，则返回一个空 AnnotationAttributes。 而不会返回 {@code null}
	 *
	 * @param constructor            构造方法
	 * @param annotationType         要查找的元注释类型
	 * @param classValueAsString     是否把元注释中的类型要素用字符串的形式表达
	 * @param nestedAnnotationsAsMap 是否把元注释中的元素要素用字符串的形式表达
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Constructor<?> constructor,
		Class<A> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {

		var annotation = findAnnotation(constructor, annotationType);
		if (annotation == null) {
			return new AnnotationAttributes();
		}
		return AnnotationAttributes.fromMap(
			AnnotationUtils.getAnnotationAttributes(annotation, classValueAsString, nestedAnnotationsAsMap));
	}

	/**
	 * 把元注释并转换成 {@link AnnotationAttributes}实例 <br>
	 *
	 * @param annotation 元注释实例
	 * @param <A>        元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes toAnnotationAttributes(@Nullable A annotation) {
		return toAnnotationAttributes(annotation, false, false);
	}

	/**
	 * 把元注释并转换成 {@link AnnotationAttributes}实例 <br>
	 *
	 * @param annotation             元注释实例
	 * @param classValueAsString     是否把元注释中的类型要素用字符串的形式表达
	 * @param nestedAnnotationsAsMap 是否把元注释中的元素要素用字符串的形式表达
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes toAnnotationAttributes(
		@Nullable A annotation,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {

		if (annotation == null) {
			return new AnnotationAttributes();
		}
		return AnnotationUtils.getAnnotationAttributes(annotation, classValueAsString, nestedAnnotationsAsMap);
	}

}
