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
package com.github.yingzhuo.turbocharger.keystore;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * {@link Converter} 实现
 *
 * @author 应卓
 * @see KeyStoreFormat#of(String)
 * @since 3.3.5
 */
public class KeyStoreFormatConverter implements Converter<String, KeyStoreFormat> {

	// 对于 string -> enum
	// EditorProperty 优先级不够，不能生效
	// ObjectToObjectConverter 优先级也不够

	/**
	 * 默认构造方法
	 */
	public KeyStoreFormatConverter() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public KeyStoreFormat convert(String source) {
		return KeyStoreFormat.of(source);
	}

}
