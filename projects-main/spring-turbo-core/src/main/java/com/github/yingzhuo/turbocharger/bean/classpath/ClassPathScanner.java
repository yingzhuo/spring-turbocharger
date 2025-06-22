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
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * ClassPath扫描器
 *
 * @author 应卓
 * @see #builder()
 * @see org.springframework.beans.factory.config.BeanDefinition
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 * @see org.springframework.core.type.filter.TypeFilter
 * @since 1.0.0
 */
@FunctionalInterface
public interface ClassPathScanner {

	/**
	 * 新建创建器
	 *
	 * @return 创建器实例
	 */
	public static ClassPathScannerBuilder builder() {
		return new ClassPathScannerBuilder();
	}

	/**
	 * 扫描类路径
	 *
	 * @param packageSet 扫描起点 (多个)
	 * @return 扫描结果
	 * @see PackageSet
	 */
	public List<AbstractBeanDefinition> scan(@Nullable PackageSet packageSet);

}
