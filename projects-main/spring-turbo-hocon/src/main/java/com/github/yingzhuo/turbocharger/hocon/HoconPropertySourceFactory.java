/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.hocon;

import com.github.yingzhuo.turbocharger.core.environment.AbstractPropertySourceFactory;
import com.github.yingzhuo.turbocharger.core.environment.YamlPropertySourceFactory;

/**
 * @author 应卓
 * @see YamlPropertySourceFactory
 * @see org.springframework.context.annotation.PropertySource
 * @see org.springframework.context.annotation.PropertySources
 * @since 2.1.3
 */
public class HoconPropertySourceFactory extends AbstractPropertySourceFactory {

	/**
	 * 默认构造方法
	 */
	public HoconPropertySourceFactory() {
		super(new HoconPropertySourceLoader());
	}

}
