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

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;

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
 * @since 3.5.3
 */
public final class ClassPathScanner {

	private final ClassPathScanningCandidateComponentProvider provider;

	/**
	 * 默认构造方法
	 */
	public ClassPathScanner() {
		this(false);
	}

	/**
	 * 构造方法
	 *
	 * @param useDefaultFilters 是否包含Spring提供的默认过滤器
	 */
	public ClassPathScanner(boolean useDefaultFilters) {
		provider = new ClassPathScanningCandidateComponentProvider(false);
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
	 * 重置所有过滤器
	 */
	public void resetFilters() {
		resetFilters(false);
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
	 * @param includeFilters 过滤器
	 * @see TypeFilter
	 * @see TypeFilterFactories
	 */
	public void addIncludeFilters(TypeFilter... includeFilters) {
		Stream.of(includeFilters)
			.filter(Objects::nonNull)
			.forEach(provider::addIncludeFilter);
	}

	/**
	 * 添加排除型过滤器
	 *
	 * @param excludeFilters 过滤器
	 * @see TypeFilter
	 * @see TypeFilterFactories
	 */
	public void addExcludeFilters(TypeFilter... excludeFilters) {
		Stream.of(excludeFilters)
			.filter(Objects::nonNull)
			.forEach(provider::addExcludeFilter);
	}

	/**
	 * 扫描类路径
	 *
	 * @param packageSet 扫描起点 (多个)
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

		return Collections.unmodifiableSet(set);
	}

}
