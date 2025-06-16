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

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.ClassUtils;
import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * ClassPath扫描器的创建器
 *
 * @author 应卓
 * @see ClassPathScanner#builder()
 * @see TypeFilter
 * @see TypeFilterFactories
 * @see Environment
 * @see ResourceLoader
 * @see ClassLoader
 * @since 1.0.0
 */
public final class ClassPathScannerBuilder {

	private final List<TypeFilter> includeFilters = new LinkedList<>();
	private final List<TypeFilter> excludeFilters = new LinkedList<>();
	private Environment environment = new StandardEnvironment();
	private ResourceLoader resourceLoader = ResourceUtils.getResourceLoader();
	private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

	/**
	 * 默认构造方法
	 */
	ClassPathScannerBuilder() {
	}

	/**
	 * 添加包含类型描述器
	 *
	 * @param filters 类型描述器
	 * @return this
	 * @see TypeFilterFactories
	 * @see org.springframework.core.type.filter.AbstractClassTestingTypeFilter
	 * @see org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter
	 */
	public ClassPathScannerBuilder includeFilter(@Nullable TypeFilter... filters) {
		CollectionUtils.nullSafeAddAll(includeFilters, filters);
		return this;
	}

	/**
	 * 添加排除类型描述器
	 *
	 * @param filters 类型描述器
	 * @return this
	 * @see TypeFilterFactories
	 * @see org.springframework.core.type.filter.AbstractClassTestingTypeFilter
	 * @see org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter
	 */
	public ClassPathScannerBuilder excludeFilter(@Nullable TypeFilter... filters) {
		CollectionUtils.nullSafeAddAll(excludeFilters, filters);
		return this;
	}

	/**
	 * 添加 {@link Environment} 实例
	 *
	 * @param environment {@link Environment} 实例
	 * @return this
	 * @see org.springframework.context.EnvironmentAware
	 */
	public ClassPathScannerBuilder environment(@Nullable Environment environment) {
		environment = Objects.requireNonNullElseGet(environment, StandardEnvironment::new);
		this.environment = environment;
		return this;
	}

	/**
	 * 添加 {@link ResourceLoader} 实例
	 *
	 * @param resourceLoader {@link ResourceLoader} 实例
	 * @return this
	 * @see org.springframework.context.ResourceLoaderAware
	 */
	public ClassPathScannerBuilder resourceLoader(@Nullable ResourceLoader resourceLoader) {
		resourceLoader = Objects.requireNonNullElseGet(resourceLoader, ResourceUtils::getResourceLoader);
		this.resourceLoader = resourceLoader;
		return this;
	}

	/**
	 * 添加 {@link ClassLoader} 实例
	 *
	 * @param classLoader {@link ClassLoader} 实例
	 * @return this
	 * @see ClassUtils#getDefaultClassLoader()
	 */
	public ClassPathScannerBuilder classLoader(@Nullable ClassLoader classLoader) {
		classLoader = Objects.requireNonNullElseGet(classLoader, ClassUtils::getDefaultClassLoader);
		this.classLoader = classLoader;
		return this;
	}

	/**
	 * 创建 {@link ClassPathScanner} 实例
	 *
	 * @return {@link ClassPathScanner} 实例
	 */
	public ClassPathScanner build() {
		if (includeFilters.isEmpty()) {
			return packageSet -> List.of();
		} else {
			var scanner = new DefaultClassPathScanner();
			scanner.setIncludeTypeFilters(includeFilters);
			scanner.setExcludeTypeFilters(excludeFilters);
			scanner.setEnvironment(environment);
			scanner.setResourceLoader(resourceLoader);
			scanner.setClassLoader(classLoader);
			return scanner;
		}
	}

}
