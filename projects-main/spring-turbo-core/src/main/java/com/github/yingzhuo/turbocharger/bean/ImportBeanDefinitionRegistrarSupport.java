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
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata                       导入元注释
	 * @param importingPrimaryAnnotationType 导入元注释类型
	 * @return 导入元注释的相关信息
	 */
	protected List<AnnotationAttributes> getAnnotationAttributesList(AnnotationMetadata metadata, Class<? extends Annotation> importingPrimaryAnnotationType) {
		return getAnnotationAttributesList(metadata, importingPrimaryAnnotationType, null);
	}

	/**
	 * 获取导入元注释的相关信息
	 *
	 * @param metadata                          导入元注释
	 * @param importingPrimaryAnnotationType    导入元注释类型
	 * @param importingRepeatableAnnotationType 导入元注释类型 (repeatable)
	 * @return 导入元注释的相关信息
	 */
	protected List<AnnotationAttributes> getAnnotationAttributesList(
		AnnotationMetadata metadata,
		@NonNull Class<? extends Annotation> importingPrimaryAnnotationType,
		@Nullable Class<? extends Annotation> importingRepeatableAnnotationType
	) {
		var result = new ArrayList<AnnotationAttributes>();

		var primary = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingPrimaryAnnotationType.getName()));
		if (primary != null) {
			result.add(primary);
		}

		if (importingRepeatableAnnotationType != null) {
			var repeatable = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(importingRepeatableAnnotationType.getName()));
			if (repeatable != null) {
				Collections.addAll(result, repeatable.getAnnotationArray("value"));
			}
		}

		return result;
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
