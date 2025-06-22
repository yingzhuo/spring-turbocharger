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

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link ClassPathScanner} 默认实现
 *
 * @author 应卓
 * @since 1.0.0
 */
public class DefaultClassPathScanner implements ClassPathScanner {

	private final ClassPathScanningCandidateComponentProvider innerScanner = new ClassPathScanningCandidateComponentProvider(false);

	/**
	 * 默认构造方法
	 */
	public DefaultClassPathScanner() {
		super();
	}

	@Override
	public Set<AbstractBeanDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null || packageSet.isEmpty()) {
			return Set.of();
		}

		final Set<BeanDefinition> result = new HashSet<>();

		for (String basePackage : packageSet) {
			result.addAll(innerScanner.findCandidateComponents(basePackage));
		}

		return result.stream()
			.map(beanDef -> {
				if (beanDef instanceof AbstractBeanDefinition abstractBeanDef) {
					return abstractBeanDef;
				} else {
					return new GenericBeanDefinition(beanDef);
				}
			})
			.collect(Collectors.toSet());
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		innerScanner.setResourceLoader(resourceLoader);
	}

	public void setEnvironment(Environment environment) {
		innerScanner.setEnvironment(environment);
	}

	public void setIncludeTypeFilters(List<TypeFilter> filters) {
		filters.forEach(innerScanner::addIncludeFilter);
	}

	public void setExcludeTypeFilters(List<TypeFilter> filters) {
		filters.forEach(innerScanner::addExcludeFilter);
	}

}
