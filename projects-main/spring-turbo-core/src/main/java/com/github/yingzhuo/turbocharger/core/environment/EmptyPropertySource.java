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
package com.github.yingzhuo.turbocharger.core.environment;

import org.springframework.core.env.PropertySource;
import org.springframework.lang.Nullable;

/**
 * @author 应卓
 * @since 2.1.3
 */
public final class EmptyPropertySource extends PropertySource<Object> {

	/**
	 * 构造方法
	 *
	 * @param name 名称
	 */
	public EmptyPropertySource(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Object getProperty(String name) {
		return null;
	}

}
