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
package com.github.yingzhuo.turbocharger.util.collection;

import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.loadResourceAsInputStream;

/**
 * @author 应卓
 * @since 3.4.4
 */
public final class PropertiesUtils {

	/**
	 * 私有构造方法
	 */
	private PropertiesUtils() {
		super();
	}

	public static Properties load(String resourceLocation) {
		Assert.hasText(resourceLocation, "resourceLocation is required");

		try {
			var properties = new Properties();
			properties.load(loadResourceAsInputStream(resourceLocation));
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Properties loadFromXML(String resourceLocation) {
		Assert.hasText(resourceLocation, "resourceLocation is required");

		try {
			var properties = new Properties();
			properties.loadFromXML(loadResourceAsInputStream(resourceLocation));
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
