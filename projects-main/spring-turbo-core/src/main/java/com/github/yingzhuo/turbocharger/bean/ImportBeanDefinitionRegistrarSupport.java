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

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 3.5.3
 */
public abstract class ImportBeanDefinitionRegistrarSupport implements ImportBeanDefinitionRegistrar {

	private static final String ATTRIBUTE_NAME_VALUE = "value";

	protected final Stream<AnnotationAttributes> getAnnotationAttributesStream(AnnotationMetadata metadata, Class<? extends Annotation> importingPrimaryAnnotationType) {
		return getAnnotationAttributesStream(metadata, importingPrimaryAnnotationType, null);
	}

	protected final Stream<AnnotationAttributes> getAnnotationAttributesStream(
		AnnotationMetadata metadata,
		@NonNull Class<? extends Annotation> importingPrimaryAnnotationType,
		@Nullable Class<? extends Annotation> importingRepeatableAnnotationType
	) {
		var result = Stream.<AnnotationAttributes>of();

		var primary = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingPrimaryAnnotationType.getName()));
		if (primary != null) {
			result = Stream.concat(result, Stream.of(primary));
		}

		if (importingRepeatableAnnotationType != null) {
			var repeatable = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingRepeatableAnnotationType.getName()));
			if (repeatable != null) {
				result = Stream.concat(result, Stream.of(repeatable.getAnnotationArray(ATTRIBUTE_NAME_VALUE)));
			}
		}

		return result;
	}

}
