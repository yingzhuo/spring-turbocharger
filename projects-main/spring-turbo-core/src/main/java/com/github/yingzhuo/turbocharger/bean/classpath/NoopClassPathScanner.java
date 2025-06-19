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
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author 应卓
 * @since 3.5.0
 */
final class NoopClassPathScanner implements ClassPathScanner {

	public static NoopClassPathScanner getInstance() {
		return AsyncAvoid.INSTANCE;
	}

	/**
	 * 私有构造方法
	 */
	private NoopClassPathScanner() {
		super();
	}

	@Override
	public List<BeanDefinition> scan(@Nullable PackageSet packageSet) {
		return List.of();
	}

	// 延迟加载
	private static class AsyncAvoid {
		private static final NoopClassPathScanner INSTANCE = new NoopClassPathScanner();
	}
}
