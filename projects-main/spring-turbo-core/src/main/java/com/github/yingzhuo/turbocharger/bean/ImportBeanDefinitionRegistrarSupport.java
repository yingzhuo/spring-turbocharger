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

import com.github.yingzhuo.turbocharger.bean.classpath.ClassPathScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
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
import java.util.function.Supplier;

/**
 * {@link ImportBeanDefinitionRegistrar} 支持类
 *
 * @author 应卓
 * @see ClassPathScanner
 * @since 3.5.3
 */
public abstract class ImportBeanDefinitionRegistrarSupport
	implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware, BeanFactoryAware, BeanClassLoaderAware, EnvironmentCapable {

	protected final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
	protected final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

	private ResourceLoader resourceLoader = ApplicationResourceLoader.get();
	private Environment environment = new StandardEnvironment();
	private ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();
	private BeanFactory beanFactory = new DefaultListableBeanFactory();

	/**
	 * 默认构造方法
	 */
	public ImportBeanDefinitionRegistrarSupport() {
		super();
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
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Environment getEnvironment() {
		return this.environment;
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		try {
			doRegister(metadata, registry, beanNameGen);
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// noop
	}

	/**
	 * 注册Bean
	 *
	 * @param metadata    导入类及导入元注释信息
	 * @param registry    注册器
	 * @param beanNameGen BeanName生成器
	 * @throws Exception 尝试注册时发生的错误
	 */
	protected abstract void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception;

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata            导入元注释
	 * @param importingAnnotation 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	protected Set<AnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation) {
		var attrMap = metadata.getAnnotationAttributes(importingAnnotation.getName(), false);
		return attrMap == null ? Set.of() : Set.of(AnnotationAttributes.fromMap(attrMap));
	}

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata                     导入元信息
	 * @param importingAnnotation          导入元注释类型
	 * @param importingContainerAnnotation 导入元注释类型 (repeatable)
	 * @return 导入元注释的相关信息
	 */
	protected Set<AnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation, @Nullable Class<? extends Annotation> importingContainerAnnotation) {
		if (importingContainerAnnotation == null) {
			return getAnnotationAttributesSet(metadata, importingAnnotation);
		}

		return metadata.getMergedRepeatableAnnotationAttributes(
			importingAnnotation,
			importingContainerAnnotation,
			false,
			true
		);
	}

	/**
	 * 获取导入类
	 *
	 * @param metadata 导入元信息
	 * @return 导入类
	 */
	protected Class<?> getImportingClass(AnnotationMetadata metadata) {
		return ClassUtils.resolveClassName(metadata.getClassName(), null);
	}

	/**
	 * 获取导入类所在的包
	 *
	 * @param metadata 导入元信息
	 * @return 导入类所在的包
	 */
	protected Package getImportingClassPackage(AnnotationMetadata metadata) {
		return getImportingClass(metadata).getPackage();
	}

	/**
	 * 创建类扫描器
	 *
	 * @return 类扫描器实例
	 */
	protected ClassPathScanner createClassPathScanner() {
		return createClassPathScanner(null);
	}

	/**
	 * 创建类扫描器
	 *
	 * @param registry Bean注册器
	 * @return 类扫描器实例
	 */
	protected ClassPathScanner createClassPathScanner(@Nullable BeanDefinitionRegistry registry) {
		var scanner = registry == null ? new ClassPathScanner(false) : new ClassPathScanner(registry, false);
		scanner.setResourceLoader(resourceLoader);
		scanner.setEnvironment(environment);
		return scanner;
	}

	/**
	 * 加载资源
	 *
	 * @param location 资源位置
	 * @return 资源实例
	 */
	protected Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @return 资源的文本内容
	 */
	protected String getResourceAsString(String location) {
		return getResourceAsString(location, null);
	}

	/**
	 * 加载资源的文本内容
	 *
	 * @param location 资源位置
	 * @param charset  编码
	 * @return 资源的文本内容
	 */
	protected String getResourceAsString(String location, @Nullable Charset charset) {
		try {
			return getResource(location).getContentAsString(charset != null ? charset : StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 加载资源的二进制内容
	 *
	 * @param location 资源位置
	 * @return 二进制内容
	 */
	protected byte[] getResourceAsBytes(String location) {
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
	protected InputStream getResourceAsInputStream(String location) {
		return getResourceAsInputStream(location, -1);
	}

	/**
	 * 获取资源的输入流
	 *
	 * @param location   资源位置
	 * @param bufferSize bufferSize
	 * @return 输入流实例
	 */
	protected InputStream getResourceAsInputStream(String location, int bufferSize) {
		try {
			var in = getResource(location).getInputStream();
			return bufferSize > 0 ? new BufferedInputStream(in, bufferSize) : in;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 获取bean
	 *
	 * @param type     bean类型
	 * @param beanName bean名称
	 * @return bean
	 */
	@Nullable
	protected <T> T getBean(Class<T> type, String beanName) {
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
	protected <T> T getBean(Class<T> type) {
		try {
			return beanFactory.getBean(type);
		} catch (BeansException e) {
			return null;
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * @author 应卓
	 * @since 3.5.3
	 */
	public abstract static class BeanInstanceSupplier<T> implements Supplier<T> {

		@Override
		public final T get() {
			try {
				return doGet();
			} catch (Exception e) {
				throw new BeanCreationException(e.getMessage(), e);
			}
		}

		protected abstract T doGet() throws Exception;
	}

}
