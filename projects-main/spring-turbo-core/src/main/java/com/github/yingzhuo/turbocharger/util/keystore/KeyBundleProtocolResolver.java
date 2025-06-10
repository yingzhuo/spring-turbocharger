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
package com.github.yingzhuo.turbocharger.util.keystore;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.regex.Pattern;

/**
 * @author 应卓
 * @since 3.5.0
 */
public class KeyBundleProtocolResolver implements ProtocolResolver {

	// 支持以下两种格式
	// "keybundle:classpath:nobody.pem"
	// "keybundle:classpath:nobody.pem?keypass=123456"
	private static final Pattern LOCATION_PATTERN = Pattern.compile("^keybundle:(.*?)(?:\\?keypass=(.*))?$");

	@Nullable
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		var matcher = LOCATION_PATTERN.matcher(location);

		if (matcher.find()) {
			var realLocation = matcher.group(1);
			var keypass = matcher.group(2);
			return new KeyBundleResource(resourceLoader.getResource(realLocation), keypass);
		} else {
			return null;
		}
	}

}
