package com.github.yingzhuo.turbocharger.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.jspecify.annotations.Nullable;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

public final class AspectUtils {

	private AspectUtils() {
		super();
	}

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

	public static Object getTarget(JoinPoint joinPoint) {
		Assert.notNull(joinPoint, "joinPoint is required");

		var target = joinPoint.getTarget();
		return Optional.ofNullable(target)
			.orElseThrow(() -> new IllegalArgumentException("Cannot get proxies target"));
	}

	public static Class<?> getTargetType(JoinPoint joinPoint) {
		return getTarget(joinPoint).getClass();
	}

	@Nullable
	public static <A extends Annotation> A getMethodAnnotation(
		JoinPoint joinPoint,
		Class<A> annotationType) {
		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotation(method, annotationType);
	}

	public static <A extends Annotation> AnnotationAttributes getMethodAnnotationAttributes(
		JoinPoint joinPoint,
		Class<A> annotationType) {

		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(method, annotationType);
	}

	public static <A extends Annotation> AnnotationAttributes getMethodAnnotationAttributes(
		JoinPoint joinPoint,
		Class<A> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {

		var method = getMethod(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(method, annotationType, classValueAsString, nestedAnnotationsAsMap);
	}

	@Nullable
	public static <A extends Annotation> A getObjectTypeAnnotation(JoinPoint joinPoint, Class<A> annotationType) {
		var targetType = getTargetType(joinPoint);
		return AnnotationHelper.findAnnotation(targetType, annotationType);
	}

	public static AnnotationAttributes getTargetTypeAnnotationAttributes(
		JoinPoint joinPoint,
		Class<? extends Annotation> annotationType,
		boolean classValueAsString,
		boolean nestedAnnotationsAsMap) {
		var targetType = getTargetType(joinPoint);
		return AnnotationHelper.findAnnotationAttributes(targetType, annotationType, classValueAsString, nestedAnnotationsAsMap);
	}

}
