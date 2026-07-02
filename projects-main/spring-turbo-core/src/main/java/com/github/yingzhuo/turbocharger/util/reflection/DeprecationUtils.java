package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public final class DeprecationUtils {

	private static final Class<Deprecated> ANNOTATION_TYPE = Deprecated.class;

	private DeprecationUtils() {
	}

	public boolean isDeprecated(AnnotatedElement annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

	public boolean isDeprecated(Method annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

	public boolean isDeprecated(Class<?> annotationSupplier) {
		return AnnotationUtils.getAnnotation(annotationSupplier, ANNOTATION_TYPE) != null;
	}

}
