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
package com.github.yingzhuo.turbocharger.bean.classpath;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * ClassPath扫描器
 *
 * @author 应卓
 * @see PackageSet
 * @see GenericBeanDefinition
 * @see ClassPathScanningCandidateComponentProvider
 * @see ClassPathBeanDefinitionScanner
 * @since 3.5.3
 */
public final class ClassPathScanner {

	private final ClassPathScannerCore provider = new ClassPathScannerCore();
	private ClassLoader classLoader = ClassPathScanner.class.getClassLoader();

	/**
	 * 默认构造方法
	 */
	public ClassPathScanner() {
		super();
	}

	/**
	 * 设置资源加载器
	 *
	 * @param resourceLoader 资源加载器
	 */
	public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
		provider.setResourceLoader(Objects.requireNonNullElseGet(resourceLoader, ApplicationResourceLoader::get));
	}

	/**
	 * 设置环境
	 *
	 * @param environment 环境
	 */
	public void setEnvironment(@Nullable Environment environment) {
		provider.setEnvironment(Objects.requireNonNullElseGet(environment, StandardEnvironment::new));
	}

	/**
	 * 设置classLoader
	 *
	 * @param classLoader ClassLoader实例
	 */
	public void setClassLoader(@Nullable ClassLoader classLoader) {
		Assert.notNull(classLoader, "classLoader must not be null");
		this.classLoader = classLoader;
	}

	/**
	 * 重置所有过滤器
	 */
	public void resetFilters() {
		provider.resetFilters(false);
	}

	/**
	 * 重置所有过滤器
	 *
	 * @param useDefaultFilters 是否包含Spring提供的默认过滤器
	 */
	public void resetFilters(boolean useDefaultFilters) {
		provider.resetFilters(useDefaultFilters);
	}

	/**
	 * 添加包括型过滤器
	 *
	 * @param includeFilters 过滤器
	 * @see TypeFilter
	 * @see TypeFilterFactories
	 */
	public void addIncludeFilters(@Nullable TypeFilter... includeFilters) {
		if (includeFilters != null) {
			Stream.of(includeFilters)
				.filter(Objects::nonNull)
				.forEach(provider::addIncludeFilter);
		}
	}

	/**
	 * 添加排除型过滤器
	 *
	 * @param excludeFilters 过滤器
	 * @see TypeFilter
	 * @see TypeFilterFactories
	 */
	public void addExcludeFilters(@Nullable TypeFilter... excludeFilters) {
		if (excludeFilters != null) {
			Stream.of(excludeFilters)
				.filter(Objects::nonNull)
				.forEach(provider::addExcludeFilter);
		}
	}

	/**
	 * 扫描类路径
	 *
	 * @param packageSet 扫描起点 (多个起点)
	 * @return 扫描结果
	 * @see PackageSet
	 */
	public Set<GenericBeanDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null || packageSet.isEmpty()) {
			return Set.of();
		}

		var set = new HashSet<GenericBeanDefinition>();

		for (var basePackage : packageSet) {
			provider.findCandidateComponents(basePackage)
				.stream()
				.map(bd -> bd instanceof GenericBeanDefinition g ? g : new GenericBeanDefinition(bd))
				.forEach(set::add);
		}

		// 强行初始化clazz
		for (var beanDef : set) {
			var className = beanDef.getBeanClassName();
			if (className != null) {
				var clazz = ClassUtils.resolveClassName(className, this.classLoader);
				beanDef.setBeanClass(clazz);
			}
		}

		return Collections.unmodifiableSet(set);
	}

	/**
	 * 扫描器核心
	 */
	private static final class ClassPathScannerCore extends ClassPathScanningCandidateComponentProvider {

		/**
		 * 私有构造方法
		 */
		private ClassPathScannerCore() {
			super(false);
		}

		@Override
		protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
			boolean isCandidate = false;
			if (beanDefinition.getMetadata().isIndependent()) {
				if (!beanDefinition.getMetadata().isAnnotation()) {
					isCandidate = true;
				}
			}
			return isCandidate;
		}
	}

}
