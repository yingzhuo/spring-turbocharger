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
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * @author 应卓
 * @since 3.5.3
 */
public class ClassPathScannerCore extends ClassPathScanningCandidateComponentProvider {

	/**
	 * 默认构造方法
	 */
	public ClassPathScannerCore() {
		this(false);
	}

	/**
	 * 构造方法
	 *
	 * @param useDefaultFilters 是否启用默认类型过滤器
	 */
	public ClassPathScannerCore(boolean useDefaultFilters) {
		super(useDefaultFilters);
	}

	/**
	 * {@inheritDoc}
	 */
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
