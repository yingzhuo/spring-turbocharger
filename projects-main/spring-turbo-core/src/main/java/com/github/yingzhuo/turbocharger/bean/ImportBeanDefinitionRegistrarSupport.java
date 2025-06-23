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
import com.github.yingzhuo.turbocharger.useless.UselessAnnotationContainer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

/**
 * {@link ImportBeanDefinitionRegistrar} 支持类
 *
 * @author 应卓
 * @see ClassPathScanner
 * @since 3.5.3
 */
public abstract class ImportBeanDefinitionRegistrarSupport implements ImportBeanDefinitionRegistrar {

	protected final ResourceLoader resourceLoader;
	protected final Environment environment;

	/**
	 * 构造方法
	 *
	 * @param resourceLoader {@link ResourceLoader} 实例
	 * @param environment    {@link Environment} 实例
	 * @see org.springframework.context.ResourceLoaderAware
	 * @see org.springframework.context.EnvironmentAware
	 */
	protected ImportBeanDefinitionRegistrarSupport(ResourceLoader resourceLoader, Environment environment) {
		this.resourceLoader = resourceLoader;
		this.environment = environment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		try {
			doRegisterBeanDefinitions(metadata, registry, beanNameGen);
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

	protected abstract void doRegisterBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception;

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata            导入元注释
	 * @param importingAnnotation 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	protected Set<AnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation) {
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
	protected Set<AnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation, @Nullable Class<? extends Annotation> importingContainerAnnotation) {
		return metadata.getMergedRepeatableAnnotationAttributes(
			importingAnnotation,
			Objects.requireNonNullElse(importingContainerAnnotation, UselessAnnotationContainer.class),
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
		var scanner = new ClassPathScanner();
		scanner.setResourceLoader(resourceLoader);
		scanner.setEnvironment(environment);
		scanner.setUseDefaultFilters(false);
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
		try {
			return getResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
