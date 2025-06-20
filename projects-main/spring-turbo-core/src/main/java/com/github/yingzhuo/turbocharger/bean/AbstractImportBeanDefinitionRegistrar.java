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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 3.5.3
 */
public abstract class AbstractImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	private final List<AnnotationAttributes> importingAnnotationAttributesList = new ArrayList<>();
	protected final Environment environment;
	protected final ResourceLoader resourceLoader;
	protected final BeanFactory beanFactory;
	protected final ClassLoader beanClassLoader;

	@NonNull
	private final Class<? extends Annotation> importingAnnotationType;

	@Nullable
	private final Class<? extends Annotation> importingRepeatableAnnotationType;

	protected AbstractImportBeanDefinitionRegistrar(Environment environment, ResourceLoader resourceLoader, BeanFactory beanFactory, ClassLoader beanClassLoader, Class<? extends Annotation> importingAnnotationType) {
		this(environment, resourceLoader, beanFactory, beanClassLoader, importingAnnotationType, null);
	}

	protected AbstractImportBeanDefinitionRegistrar(Environment environment, ResourceLoader resourceLoader, BeanFactory beanFactory, ClassLoader beanClassLoader, Class<? extends Annotation> importingAnnotationType, @Nullable Class<? extends Annotation> importingRepeatableAnnotationType) {
		this.environment = environment;
		this.resourceLoader = resourceLoader;
		this.beanFactory = beanFactory;
		this.beanClassLoader = beanClassLoader;
		this.importingAnnotationType = importingAnnotationType;
		this.importingRepeatableAnnotationType = importingRepeatableAnnotationType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
		this.setupImportingAttributes(metadata);

		for (AnnotationAttributes attributes : this.importingAnnotationAttributesList) {
			this.handleAnnotationAttributes(attributes, registry, beanNameGenerator);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// noop
	}

	private void setupImportingAttributes(AnnotationMetadata metadata) {
		var singleAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingAnnotationType.getName()));
		Optional.ofNullable(singleAttributes).ifPresent(it -> importingAnnotationAttributesList.add(singleAttributes));

		if (importingRepeatableAnnotationType != null) {
			var listAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingRepeatableAnnotationType.getName()));
			if (listAttributes != null) {
				Collections.addAll(importingAnnotationAttributesList, listAttributes.getAnnotationArray("value"));
			}
		}
	}

	protected abstract void handleAnnotationAttributes(AnnotationAttributes attributes, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator);

}
