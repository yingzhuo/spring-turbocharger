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

import com.github.yingzhuo.turbocharger.util.ClassUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;

/**
 * {@link ClassPathScanner} 默认实现
 *
 * @author 应卓
 * @since 1.0.0
 */
final class DefaultClassPathScanner implements ClassPathScanner {

	private final ClassPathScannerCore provider = new ClassPathScannerCore();
	private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

	/**
	 * 构造方法
	 */
	DefaultClassPathScanner() {
	}

	@Override
	public List<ClassDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null) {
			return List.of();
		}

		final List<BeanDefinition> list = new ArrayList<>();

		for (String basePackage : packageSet) {
			nullSafeAddAll(list, provider.findCandidateComponents(basePackage));
		}

		return list.stream().map(bd -> new ClassDefinition(bd, classLoader))
			.distinct()
			.sorted(Comparator.naturalOrder())
			.toList();
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		provider.setResourceLoader(resourceLoader);
	}

	public void setEnvironment(Environment environment) {
		provider.setEnvironment(environment);
	}

	public void setIncludeTypeFilters(List<TypeFilter> filters) {
		filters.forEach(provider::addIncludeFilter);
	}

	public void setExcludeTypeFilters(List<TypeFilter> filters) {
		filters.forEach(provider::addExcludeFilter);
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	// -----------------------------------------------------------------------------------------------------------------

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
