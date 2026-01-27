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
package com.github.yingzhuo.turbocharger.core.io;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author 应卓
 * @see PemResource
 * @see org.springframework.boot.ssl.pem.PemContent
 * @since 3.5.4
 */
public class PemResourceProtocolResolver implements ProtocolResolver {

	private static final Pattern PATTERN = Pattern.compile("^pem:(.+?)(?:\\?keypass=(.*))?$");

	/**
	 * 默认构造方法
	 */
	public PemResourceProtocolResolver() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		var matcher = PATTERN.matcher(location);
		if (matcher.find()) {
			var resourceLocation = matcher.group(1);  // 必选
			var keypass = matcher.group(2); // 可选

			if (!StringUtils.hasText(resourceLocation)) {
				return null;
			}

			if (keypass == null || keypass.isEmpty()) {
				keypass = null;
			}

			var delegatingResource = resourceLoader.getResource(resourceLocation);
			return new PemResource(delegatingResource, keypass);
		}
		return null;
	}
}
