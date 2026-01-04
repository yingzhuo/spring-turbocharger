/*
 * Copyright 2022-2026 the original author or authors.
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
 */
package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * 类型加载异常提供器
 *
 * @author 应卓
 * @see ClassUtils
 * @see ClassLoadingException
 * @since 1.0.2
 */
public class ClassLoadingExceptionSupplier implements Supplier<ClassLoadingException> {

	private final String className;

	/**
	 * 构造方法
	 *
	 * @param className 加载类型名称
	 */
	public ClassLoadingExceptionSupplier(String className) {
		Assert.hasText(className, "className is required");
		this.className = className;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClassLoadingException get() {
		var msg = StringFormatter.format("not able to load class. class name: '{}'", className);
		return new ClassLoadingException(msg);
	}

}
