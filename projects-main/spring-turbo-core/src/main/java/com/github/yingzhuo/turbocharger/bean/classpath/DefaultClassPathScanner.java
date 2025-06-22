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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;

/**
 * {@link ClassPathScanner} 默认实现
 *
 * @author 应卓
 * @since 1.0.0
 */
public class DefaultClassPathScanner implements ClassPathScanner {

	private final InnerScanner innerScanner = new InnerScanner();

	/**
	 * 默认构造方法
	 */
	public DefaultClassPathScanner() {
		super();
	}

	@Override
	public List<AbstractBeanDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null || packageSet.isEmpty()) {
			return List.of();
		}

		List<BeanDefinition> list = new ArrayList<>();

		for (String basePackage : packageSet) {
			nullSafeAddAll(list, innerScanner.findCandidateComponents(basePackage));
		}

		return list.stream()
			.map(beanDef -> {
				if (beanDef instanceof AbstractBeanDefinition abstractBeanDef) {
					return abstractBeanDef;
				} else {
					return new GenericBeanDefinition(beanDef);
				}
			}).toList();
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

	private static final class InnerScanner extends ClassPathScanningCandidateComponentProvider {

		private InnerScanner() {
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
