package com.github.yingzhuo.turbocharger.bean;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

public final class ImportingUtils {

	private ImportingUtils() {
		super();
	}

	public static Set<AnnotationAttributes> getAnnotationAttributesSet(
		AnnotationMetadata metadata,
		Class<? extends Annotation> importingAnnotation) {
		return getAnnotationAttributesSet(metadata, importingAnnotation, null);
	}

	public static Set<AnnotationAttributes> getAnnotationAttributesSet(
		AnnotationMetadata metadata,
		Class<? extends Annotation> importingAnnotation,
		@Nullable Class<? extends Annotation> importingContainerAnnotation) {

		Assert.notNull(metadata, "metadata must not be null");
		Assert.notNull(metadata, "importingAnnotation must not be null");

		if (importingContainerAnnotation == null) {
			var attrMap = metadata.getAnnotationAttributes(importingAnnotation.getName(), false);
			return attrMap == null ? Set.of() : Set.of(AnnotationAttributes.fromMap(attrMap));
		}

		return metadata.getMergedRepeatableAnnotationAttributes(importingAnnotation, importingContainerAnnotation, false);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String getImportingClassName(ClassMetadata metadata) {
		Assert.notNull(metadata, "metadata must not be null");
		return metadata.getClassName();
	}

	public static Class<?> getImportingClass(ClassMetadata metadata) {
		return ClassUtils.resolveClassName(getImportingClassName(metadata), null);
	}

	public static Package getImportingClassPackage(ClassMetadata metadata) {
		return getImportingClass(metadata).getPackage();
	}

	@Nullable
	public static <A extends Annotation> A getAnnotationOfImportingClass(ClassMetadata metadata, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(getImportingClass(metadata), annotationType);
	}

}
