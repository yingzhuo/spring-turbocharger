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
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.lang.Nullable;

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
 * @see ImportSelector
 * @see ImportBeanDefinitionRegistrar
 * @see Resource
 * @see Environment
 * @see BeanFactory
 * @since 3.5.3
 */
public abstract class AbstractImportingSupport
	implements ResourceLoaderAware, EnvironmentAware, BeanFactoryAware, BeanClassLoaderAware {

	private ResourceLoader resourceLoader = ApplicationResourceLoader.get();
	private Environment environment = new StandardEnvironment();
	private BeanFactory beanFactory = new DefaultListableBeanFactory();
	private ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();

	/**
	 * 默认构造方法
	 */
	public AbstractImportingSupport() {
		super();
	}

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
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public final Environment getEnvironment() {
		return this.environment;
	}

	public final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public final BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public final ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	// AnnotationMetadata相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata            导入元信息
	 * @param importingAnnotation 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	public final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation) {
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
	public final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation, @Nullable Class<? extends Annotation> importingContainerAnnotation) {
		return ImportingUtils.getAnnotationAttributesSet(metadata, importingAnnotation, importingContainerAnnotation)
			.stream()
			.map(a -> new SmartAnnotationAttributes(environment, a))
			.collect(Collectors.toUnmodifiableSet());
	}

	/**
	 * 获取导入类的名称
	 *
	 * @param metadata 导入元信息
	 * @return 导入类的名称
	 */
	public final String getImportingClassName(ClassMetadata metadata) {
		return ImportingUtils.getImportingClassName(metadata);
	}

	/**
	 * 获取导入类
	 *
	 * @param metadata 导入元信息
	 * @return 导入类
	 */
	public final Class<?> getImportingClass(ClassMetadata metadata) {
		return ImportingUtils.getImportingClass(metadata);
	}

	/**
	 * 获取导入类所在的包
	 *
	 * @param metadata 导入元信息
	 * @return 导入类所在的包
	 */
	public final Package getImportingClassPackage(ClassMetadata metadata) {
		return ImportingUtils.getImportingClassPackage(metadata);
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
	public final <A extends Annotation> A getAnnotationOfImportingClass(ClassMetadata metadata, Class<A> annotationType) {
		return ImportingUtils.getAnnotationOfImportingClass(metadata, annotationType);
	}

	// ResourceLoader相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 加载资源
	 *
	 * @param location 资源位置
	 * @return 资源实例
	 */
	public final Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @return 资源的文本内容
	 */
	public final String getResourceAsString(String location) {
		return getResourceAsString(location, (Charset) null);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	public final String getResourceAsString(String location, @Nullable String charset) {
		return getResourceAsString(location, Charset.forName(charset != null ? charset : "UTF-8"));
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	public final String getResourceAsString(String location, @Nullable Charset charset) {
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
	public final Stream<String> getResourceAsLines(String location) {
		return getResourceAsLines(location, (Charset) null);
	}

	/**
	 * 加载资源的所有文本行
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	public final Stream<String> getResourceAsLines(String location, @Nullable Charset charset) {
		return getResourceAsString(location, charset).lines();
	}

	/**
	 * 加载资源的所有文本行
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	public final Stream<String> getResourceAsLines(String location, @Nullable String charset) {
		return getResourceAsString(location, charset).lines();
	}

	/**
	 * 加载资源的二进制内容
	 *
	 * @param location 资源位置
	 * @return 二进制内容
	 */
	public final byte[] getResourceAsBytes(String location) {
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
	public final InputStream getResourceAsInputStream(String location) {
		return getResourceAsInputStream(location, -1);
	}

	/**
	 * 获取资源的输入流
	 *
	 * @param location   资源位置
	 * @param bufferSize bufferSize
	 * @return 输入流实例
	 */
	public final InputStream getResourceAsInputStream(String location, int bufferSize) {
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
	public final <T> T getBean(Class<T> type, String beanName) {
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
	public final <T> T getBean(Class<T> type) {
		try {
			return beanFactory.getBean(type);
		} catch (BeansException e) {
			return null;
		}
	}

}
