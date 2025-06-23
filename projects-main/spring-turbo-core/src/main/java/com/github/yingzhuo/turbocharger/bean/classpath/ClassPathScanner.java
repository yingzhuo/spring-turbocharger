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

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
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
 * @since 3.5.3
 */
public final class ClassPathScanner {

	private final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

	public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
		provider.setResourceLoader(Objects.requireNonNullElseGet(resourceLoader, DefaultResourceLoader::new));
	}

	public void setEnvironment(@Nullable Environment environment) {
		provider.setEnvironment(Objects.requireNonNullElseGet(environment, StandardEnvironment::new));
	}

	public void setUseDefaultFilters(boolean useDefaultFilters) {
		provider.resetFilters(useDefaultFilters);
	}

	public void setResourcePattern(String resourcePattern) {
		provider.setResourcePattern(resourcePattern);
	}

	public void addIncludeFilters(TypeFilter... includeFilters) {
		Stream.of(includeFilters)
			.filter(Objects::nonNull)
			.forEach(provider::addIncludeFilter);
	}

	public void addExcludeFilters(TypeFilter... includeFilters) {
		Stream.of(includeFilters)
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
	public Set<AbstractBeanDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null || packageSet.isEmpty()) {
			return Set.of();
		}

		var set = new HashSet<AbstractBeanDefinition>();

		for (var basePackage : packageSet) {
			provider.findCandidateComponents(basePackage)
				.stream()
				.map(bd -> {
					if (bd instanceof AbstractBeanDefinition abd) {
						return abd;
					}
					return new GenericBeanDefinition(bd);
				})
				.forEach(set::add);
		}
		return Collections.unmodifiableSet(set);
	}

}
