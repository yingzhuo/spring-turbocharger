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

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author 应卓
 * @see org.springframework.context.annotation.ImportAware#setImportMetadata(AnnotationMetadata)
 * @since 3.5.3
 */
public final class AnnotationAttributesUtils {

	/**
	 * 私有构造方法
	 */
	private AnnotationAttributesUtils() {
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

		Assert.notNull(metadata, "metadata must not be null");
		Assert.notNull(metadata, "importingAnnotation must not be null");

		var attrMap = metadata.getAnnotationAttributes(importingAnnotation.getName(), false);

		if (attrMap == null) {
			return Set.of();
		} else {
			return Set.of(AnnotationAttributes.fromMap(attrMap));
		}
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
			return getAnnotationAttributesSet(metadata, importingAnnotation);
		}

		return metadata.getMergedRepeatableAnnotationAttributes(importingAnnotation, importingContainerAnnotation, false);
	}

}
