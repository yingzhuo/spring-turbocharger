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
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link ImportBeanDefinitionRegistrar} 支持类
 *
 * @author 应卓
 * @since 3.5.3
 */
public abstract class ImportBeanDefinitionRegistrarSupport extends AbstractImportingSupport implements ImportBeanDefinitionRegistrar {

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
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		try {
			doRegister(metadata, registry, beanNameGen);
		} catch (BeanCreationException e) {
			throw e;
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// 不允许覆盖此方法
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

	// ClassPathScanner相关
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 创建类扫描器
	 *
	 * @return 类扫描器实例
	 */
	public final ClassPathScanner createClassPathScanner() {
		return createClassPathScanner(false);
	}

	/**
	 * 创建类扫描器
	 *
	 * @param useDefaultFilters 是否启用默认类型过滤器
	 * @return 类扫描器实例
	 */
	public final ClassPathScanner createClassPathScanner(boolean useDefaultFilters) {
		var scanner = new ClassPathScanner(useDefaultFilters);
		scanner.setClassLoader(getBeanClassLoader());
		scanner.setResourceLoader(getResourceLoader());
		scanner.setEnvironment(getEnvironment());
		return scanner;
	}

}
