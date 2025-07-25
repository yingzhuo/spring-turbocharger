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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * AOP相关工具
 *
 * @author 应卓
 * @see AnnotationHelper
 * @since 2.1.1
 */
public final class AspectUtils {

	/**
	 * 私有构造方法
	 */
	private AspectUtils() {
		super();
	}

	/**
	 * 获取切面方法
	 *
	 * @param joinPoint {@link JoinPoint} 实例
	 * @return 切面方法
	 * @throws IllegalArgumentException 不能获取切面方法
	 */
	public static Method getMethod(JoinPoint joinPoint) {
		Assert.notNull(joinPoint, "joinPoint is required");
		var signature = joinPoint.getSignature();

		return Optional.ofNullable(signature)
			.map(s -> {
				if (s instanceof MethodSignature ms) {
					return ms.getMethod();
				} else {
					return null;
				}
			})
			.orElseThrow(() -> new IllegalArgumentException("Cannot get proxies method"));
	}

	/**
	 * 获取切面拦截对象
	 *
	 * @param joinPoint {@link JoinPoint} 实例
	 * @return 切面拦截对象
	 */
	public static Object getTarget(JoinPoint joinPoint) {
		Assert.notNull(joinPoint, "joinPoint is required");

		var target = joinPoint.getTarget();
		return Optional.ofNullable(target)
			.orElseThrow(() -> new IllegalArgumentException("Cannot get proxies target"));
	}

	/**
	 * 获取切面拦截对象类型
	 *
	 * @param joinPoint {@link JoinPoint} 实例
	 * @return 切面拦截对象类型
	 */
	public static Class<?> getTargetType(JoinPoint joinPoint) {
		return getTarget(joinPoint).getClass();
	}

	/**
	 * 查找被拦截方法上的元注释
	 *
	 * @param joinPoint      {@link JoinPoint} 实例
	 * @param annotationType 元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例或 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A getMethodAnnotation(
		JoinPoint joinPoint,
		Class<A> annotationType) {
		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotation(method, annotationType);
	}

	/**
	 * 获取被拦截方法上的元注释相关的 {@link AnnotationAttributes} 实例
	 *
	 * @param joinPoint      {@link JoinPoint} 实例
	 * @param annotationType 元注释类型
	 * @param <A>            元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes getMethodAnnotationAttributes(
		JoinPoint joinPoint,
		Class<A> annotationType) {

		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(method, annotationType);
	}

	/**
	 * 获取被拦截方法上的元注释相关的 {@link AnnotationAttributes} 实例
	 *
	 * @param joinPoint              {@link JoinPoint} 实例
	 * @param annotationType         元注释类型
	 * @param classValueAsString     {@link Class} 输出为字符串型
	 * @param nestedAnnotationsAsMap 元注释中内嵌的元注释输出位 {@link java.util.Map} 类型
	 * @param <A>                    元注释类型泛型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static <A extends Annotation> AnnotationAttributes getMethodAnnotationAttributes(
		JoinPoint joinPoint,
		Class<A> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {

		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(method, annotationType, classValueAsString, nestedAnnotationsAsMap);
	}

	/**
	 * 查找被拦截对象类型上的元注释
	 *
	 * @param joinPoint      {@link JoinPoint} 实例
	 * @param annotationType 元注释类型
	 * @param <A>            元注释类型泛型
	 * @return 元注释实例或 {@code null}
	 */
	@Nullable
	public static <A extends Annotation> A getObjectTypeAnnotation(JoinPoint joinPoint, Class<A> annotationType) {
		var targetType = getTargetType(joinPoint);
		return AnnotationHelper.findAnnotation(targetType, annotationType);
	}

	/**
	 * 获取被拦截对象类型上的元注释相关的 {@link AnnotationAttributes} 实例
	 *
	 * @param joinPoint      {@link JoinPoint} 实例
	 * @param annotationType 元注释类型
	 * @return {@link AnnotationAttributes} 实例
	 */
	public static AnnotationAttributes getTargetTypeAnnotationAttributes(
		JoinPoint joinPoint,
		Class<? extends Annotation> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {
		var targetType = getTargetType(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(targetType, annotationType, classValueAsString, nestedAnnotationsAsMap);
	}

}
