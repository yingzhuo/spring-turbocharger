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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see org.springframework.context.annotation.ImportSelector
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @since 3.5.3
 */
public abstract class AbstractImportingSupport
	implements ResourceLoaderAware, EnvironmentAware, BeanFactoryAware, EnvironmentCapable {

	private ResourceLoader resourceLoader = ApplicationResourceLoader.get();
	private Environment environment = new StandardEnvironment();
	private BeanFactory beanFactory = new DefaultListableBeanFactory();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Environment getEnvironment() {
		return this.environment;
	}

	public final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public final BeanFactory getBeanFactory() {
		return beanFactory;
	}

	// AnnotationMetadata相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata            导入元注释
	 * @param importingAnnotation 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	protected final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation) {
		var attrMap = metadata.getAnnotationAttributes(importingAnnotation.getName(), false);

		if (attrMap == null) {
			return Set.of();
		} else {
			return Set.of(new SmartAnnotationAttributes(environment, AnnotationAttributes.fromMap(attrMap)));
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
	protected final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation, @Nullable Class<? extends Annotation> importingContainerAnnotation) {
		if (importingContainerAnnotation == null) {
			return getAnnotationAttributesSet(metadata, importingAnnotation);
		}

		return metadata
			.getMergedRepeatableAnnotationAttributes(importingAnnotation, importingContainerAnnotation, false, true)
			.stream()
			.map(attr -> new SmartAnnotationAttributes(environment, attr))
			.collect(Collectors.toSet());
	}

	/**
	 * 获取导入类的名称
	 *
	 * @param metadata 导入元信息
	 * @return 导入类的名称
	 */
	public final String getImportingClassName(AnnotationMetadata metadata) {
		return metadata.getClassName();
	}

	/**
	 * 获取导入类
	 *
	 * @param metadata 导入元信息
	 * @return 导入类
	 */
	protected final Class<?> getImportingClass(AnnotationMetadata metadata) {
		return ClassUtils.resolveClassName(getImportingClassName(metadata), ClassUtils.getDefaultClassLoader());
	}

	/**
	 * 获取导入类所在的包
	 *
	 * @param metadata 导入元信息
	 * @return 导入类所在的包
	 */
	protected final Package getImportingClassPackage(AnnotationMetadata metadata) {
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
	protected final <A extends Annotation> A getAnnotationOfImportingClass(AnnotationMetadata metadata, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(getImportingClass(metadata), annotationType);
	}

	// ResourceLoader相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 加载资源
	 *
	 * @param location 资源位置
	 * @return 资源实例
	 */
	protected final Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @return 资源的文本内容
	 */
	protected final String getResourceAsString(String location) {
		return getResourceAsString(location, null);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	protected final String getResourceAsString(String location, @Nullable Charset charset) {
		try {
			return getResource(location).getContentAsString(charset != null ? charset : StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 加载资源的所有文本行
	 *
	 * @param location 资源位置
	 * @return 资源的文本内容
	 */
	protected final Stream<String> getResourceAsLines(String location) {
		return getResourceAsLines(location, null);
	}

	/**
	 * 加载资源的所有文本行
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	protected final Stream<String> getResourceAsLines(String location, @Nullable Charset charset) {
		return getResourceAsString(location, charset).lines();
	}

	/**
	 * 加载资源的二进制内容
	 *
	 * @param location 资源位置
	 * @return 二进制内容
	 */
	protected final byte[] getResourceAsBytes(String location) {
		try {
			return getResource(location).getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 获取资源的输入流
	 *
	 * @param location 资源位置
	 * @return 输入流实例
	 */
	protected final InputStream getResourceAsInputStream(String location) {
		return getResourceAsInputStream(location, -1);
	}

	/**
	 * 获取资源的输入流
	 *
	 * @param location   资源位置
	 * @param bufferSize bufferSize
	 * @return 输入流实例
	 */
	protected final InputStream getResourceAsInputStream(String location, int bufferSize) {
		try {
			var in = getResource(location).getInputStream();
			return bufferSize > 0 ? new BufferedInputStream(in, bufferSize) : in;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	// BeanFactory相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 获取bean
	 *
	 * @param type     bean类型
	 * @param beanName bean名称
	 * @return bean
	 */
	@Nullable
	protected final <T> T getBean(Class<T> type, String beanName) {
		try {
			return beanFactory.getBean(beanName, type);
		} catch (BeansException e) {
			return null;
		}
	}

	/**
	 * 获取bean
	 *
	 * @param type bean类型
	 * @return bean
	 */
	@Nullable
	protected final <T> T getBean(Class<T> type) {
		try {
			return beanFactory.getBean(type);
		} catch (BeansException e) {
			return null;
		}
	}

}
