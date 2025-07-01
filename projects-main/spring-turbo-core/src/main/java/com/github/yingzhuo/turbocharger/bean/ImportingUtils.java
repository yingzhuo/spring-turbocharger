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
package com.github.yingzhuo.turbocharger.bean;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author 应卓
 * @see Import
 * @see AnnotationMetadata
 * @see ClassMetadata
 * @see ImportAware#setImportMetadata(AnnotationMetadata)
 * @since 3.5.3
 */
public final class ImportingUtils {

	/**
	 * 私有构造方法
	 */
	private ImportingUtils() {
		super();
	}

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata            导入元信息
	 * @param importingAnnotation 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	public static Set<AnnotationAttributes> getAnnotationAttributesSet(
		AnnotationMetadata metadata,
		Class<? extends Annotation> importingAnnotation) {
		return getAnnotationAttributesSet(metadata, importingAnnotation, null);
	}

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata                     导入元信息
	 * @param importingAnnotation          导入元注释类型
	 * @param importingContainerAnnotation 导入元注释类型 (repeatable)
	 * @return 导入元注释的相关信息
	 */
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

	/**
	 * 获取导入类的名称
	 *
	 * @param metadata 导入元信息
	 * @return 导入类的名称
	 */
	public static String getImportingClassName(ClassMetadata metadata) {
		Assert.notNull(metadata, "metadata must not be null");
		return metadata.getClassName();
	}

	/**
	 * 获取导入类
	 *
	 * @param metadata 导入元信息
	 * @return 导入类
	 */
	public static Class<?> getImportingClass(ClassMetadata metadata) {
		return ClassUtils.resolveClassName(getImportingClassName(metadata), null);
	}

	/**
	 * 获取导入类所在的包
	 *
	 * @param metadata 导入元信息
	 * @return 导入类所在的包
	 */
	public static Package getImportingClassPackage(ClassMetadata metadata) {
		return getImportingClass(metadata).getPackage();
	}

	/**
	 * 获取导入类上的元注释
	 *
	 * @param metadata       导入元信息
	 * @param annotationType 要查找的元注释类型
	 * @return 结果
	 * @see AnnotationUtils#findAnnotation(Class, Class)
	 */
	@Nullable
	public static <A extends Annotation> A getAnnotationOfImportingClass(ClassMetadata metadata, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(getImportingClass(metadata), annotationType);
	}

}
