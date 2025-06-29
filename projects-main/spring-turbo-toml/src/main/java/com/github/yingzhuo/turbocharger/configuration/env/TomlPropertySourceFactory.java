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
package com.github.yingzhuo.turbocharger.configuration.env;

import com.github.yingzhuo.turbocharger.core.configuration.AbstractPropertySourceFactory;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * @author 应卓
 * @see org.springframework.context.annotation.PropertySource
 * @see org.springframework.context.annotation.PropertySources
 * @since 3.4.5
 */
public class TomlPropertySourceFactory extends AbstractPropertySourceFactory implements PropertySourceFactory {

	/**
	 * 默认构造方法
	 */
	public TomlPropertySourceFactory() {
		super(new TomlPropertySourceLoader());
	}

}
