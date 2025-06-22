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

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 3.5.3
 */
public abstract class AbstractImportBeanDefinitionRegistrar
	implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware, ResourceLoaderAware, BeanClassLoaderAware {

	private final List<AnnotationAttributes> importingAnnotationAttributesList = new ArrayList<>();
	protected Environment environment = new StandardEnvironment();
	protected ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();
	protected ResourceLoader resourceLoader = ApplicationResourceLoader.get(beanClassLoader);
	protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(resourceLoader);
	protected BeanFactory beanFactory = new DefaultListableBeanFactory();

	@NonNull
	private final String importingAnnotationType;

	@Nullable
	private final String importingRepeatableAnnotationType;

	private boolean ignoreExceptions = false;

	/**
	 * 构造方法
	 *
	 * @param importingAnnotationType 导入元注释类型
	 */
	protected AbstractImportBeanDefinitionRegistrar(Class<? extends Annotation> importingAnnotationType) {
		this(importingAnnotationType, null);
	}

	/**
	 * 构造方法
	 *
	 * @param importingAnnotationType           导入元注释类型
	 * @param importingRepeatableAnnotationType 导入元注释类型 (Repeatable)
	 */
	protected AbstractImportBeanDefinitionRegistrar(Class<? extends Annotation> importingAnnotationType, @Nullable Class<? extends Annotation> importingRepeatableAnnotationType) {
		Assert.notNull(importingAnnotationType, "importingAnnotationType must not be null");
		this.importingAnnotationType = importingAnnotationType.getName();
		this.importingRepeatableAnnotationType = Optional.ofNullable(importingRepeatableAnnotationType)
			.map(Class::getName)
			.orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
		this.setupImportingAttributes(metadata);

		for (AnnotationAttributes attributes : this.importingAnnotationAttributesList) {
			try {
				handleAnnotationAttributes(attributes, registry, beanNameGenerator);
			} catch (Exception e) {
				if (!ignoreExceptions) {
					throw new BeanInitializationException(e.getMessage(), e);
				}
			}
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
		var singleAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingAnnotationType));
		if (singleAttributes != null) {
			importingAnnotationAttributesList.add(singleAttributes);
		}

		if (importingRepeatableAnnotationType != null) {
			var listAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingRepeatableAnnotationType));
			if (listAttributes != null) {
				Collections.addAll(importingAnnotationAttributesList, listAttributes.getAnnotationArray("value"));
			}
		}
	}

	protected abstract void handleAnnotationAttributes(AnnotationAttributes attr, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) throws Exception;

	public void setIgnoreExceptions(boolean ignoreExceptions) {
		this.ignoreExceptions = ignoreExceptions;
	}

	@Override
	public final void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public final void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
	}

	@Override
	public final void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public final void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	protected final InputStream loadResourceAsStream(String location) {
		try {
			return resourceLoader.getResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	protected final String loadResourceAsString(String location, @Nullable Charset charset) {
		try {
			return resourceLoader.getResource(location).getContentAsString(charset != null ? charset : UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
