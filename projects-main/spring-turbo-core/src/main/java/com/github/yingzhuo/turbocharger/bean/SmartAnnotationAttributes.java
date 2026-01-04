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
package com.github.yingzhuo.turbocharger.bean;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.stream.Stream;

/**
 * {@link AnnotationAttributes} 装饰器
 *
 * @author 应卓
 * @since 3.5.3
 */
public class SmartAnnotationAttributes extends AnnotationAttributes {

	private final Environment environment;

	/**
	 * 构造方法
	 *
	 * @param environment {@link Environment} 实例
	 * @param other       需要包装的 {@link AnnotationAttributes} 实例
	 */
	public SmartAnnotationAttributes(Environment environment, AnnotationAttributes other) {
		super(other);
		this.environment = environment;
	}

	/**
	 * 构造方法
	 *
	 * @param environment {@link Environment} 实例
	 * @param other       需要包装的 {@link AnnotationAttributes} 实例
	 */
	public SmartAnnotationAttributes(Environment environment, Map<String, Object> other) {
		this(environment, new AnnotationAttributes(other));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String attributeName) {
		var text = super.getString(attributeName);
		return environment.resolvePlaceholders(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getStringArray(String attributeName) {
		var array = super.getStringArray(attributeName);
		return Stream.of(array)
			.map(environment::resolvePlaceholders)
			.toArray(String[]::new);
	}

}
