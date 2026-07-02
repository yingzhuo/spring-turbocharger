package com.github.yingzhuo.turbocharger.core;

import org.jspecify.annotations.Nullable;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public final class AnnotationHelper {

	private AnnotationHelper() {
		super();
	}

	@Nullable
	public static <A extends Annotation> A findAnnotation(Class<?> type, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(type, annotationType);
	}

	public static <A extends Annotation> A findRequiredAnnotation(Class<?> type, Class<A> annotationType) {
		var annotation = findAnnotation(type, annotationType);
		return Objects.requireNonNull(annotation);
	}

	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Class<?> type,
		Class<A> annotationType) {
		return findAnnotationAttributes(type, annotationType, false, false);
	}

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

	@Nullable
	public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(method, annotationType);
	}

	public static <A extends Annotation> A findRequiredAnnotation(Method method, Class<A> annotationType) {
		var annotation = AnnotationUtils.findAnnotation(method, annotationType);
		return Objects.requireNonNull(annotation);
	}

	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Method method,
		Class<A> annotationType) {

		return findAnnotationAttributes(method, annotationType, false, false);
	}

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

	@Nullable
	public static <A extends Annotation> A findAnnotation(Field field, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(field, annotationType);
	}

	public static <A extends Annotation> A findRequiredAnnotation(Field field, Class<A> annotationType) {
		var annotation = AnnotationUtils.findAnnotation(field, annotationType);
		return Objects.requireNonNull(annotation);
	}

	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Field field,
		Class<A> annotationType) {
		return findAnnotationAttributes(field, annotationType, false, false);
	}

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

	@Nullable
	public static <A extends Annotation> A findAnnotation(Constructor<?> constructor, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(constructor, annotationType);
	}

	public static <A extends Annotation> A findRequiredAnnotation(Constructor<?> constructor, Class<A> annotationType) {
		var annotation = findAnnotation(constructor, annotationType);
		return Objects.requireNonNull(annotation);
	}

	public static <A extends Annotation> AnnotationAttributes findAnnotationAttributes(
		Constructor<?> constructor,
		Class<A> annotationType) {

		return findAnnotationAttributes(constructor, annotationType, false, false);
	}

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

	public static <A extends Annotation> AnnotationAttributes toAnnotationAttributes(@Nullable A annotation) {
		return toAnnotationAttributes(annotation, false, false);
	}

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
