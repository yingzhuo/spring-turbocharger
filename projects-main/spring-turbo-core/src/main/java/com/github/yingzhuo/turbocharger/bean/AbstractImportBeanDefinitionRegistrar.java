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
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 3.5.3
 */
public abstract class AbstractImportBeanDefinitionRegistrar
	implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanFactoryAware, ResourceLoaderAware, BeanClassLoaderAware {

	private final List<AnnotationAttributes> importingAnnotationAttributesList = new ArrayList<>();
	private Environment environment = new StandardEnvironment();
	private ResourceLoader resourceLoader = ApplicationResourceLoader.get();
	private BeanFactory beanFactory = new DefaultListableBeanFactory();
	private ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();

	@NonNull
	private final Class<? extends Annotation> importingAnnotationType;

	@Nullable
	private final Class<? extends Annotation> importingRepeatableAnnotationType;

	private boolean ignoreExceptions = false;

	protected AbstractImportBeanDefinitionRegistrar(Class<? extends Annotation> importingAnnotationType) {
		this(importingAnnotationType, null);
	}

	protected AbstractImportBeanDefinitionRegistrar(Class<? extends Annotation> importingAnnotationType, @Nullable Class<? extends Annotation> importingRepeatableAnnotationType) {
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
			try {
				this.handleAnnotationAttributes(attributes, registry, beanNameGenerator);
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
		var singleAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingAnnotationType.getName()));
		if (singleAttributes != null) {
			importingAnnotationAttributesList.add(singleAttributes);
		}

		if (importingRepeatableAnnotationType != null) {
			var listAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingRepeatableAnnotationType.getName()));
			if (listAttributes != null) {
				Collections.addAll(importingAnnotationAttributesList, listAttributes.getAnnotationArray("value"));
			}
		}
	}

	protected abstract void handleAnnotationAttributes(AnnotationAttributes attributes, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) throws Exception;

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
	}

	@Override
	public final void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public final void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	protected final Environment getEnvironment() {
		return environment;
	}

	protected final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	protected final BeanFactory getBeanFactory() {
		return beanFactory;
	}

	protected final ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	protected final InputStream loadResourceAsStream(String location) {
		try {
			return resourceLoader.getResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	protected final String loadResourceAsString(String location) {
		return loadResourceAsString(location, UTF_8);
	}

	protected final String loadResourceAsString(String location, @Nullable Charset charset) {
		try {
			return resourceLoader.getResource(location).getContentAsString(charset != null ? charset : UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
