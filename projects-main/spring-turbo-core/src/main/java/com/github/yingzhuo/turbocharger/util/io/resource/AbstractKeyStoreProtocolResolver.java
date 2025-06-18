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
package com.github.yingzhuo.turbocharger.util.io.resource;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author 应卓
 * @since 3.5.0
 */
public abstract sealed class AbstractKeyStoreProtocolResolver
	implements ProtocolResolver
	permits JKSResourceProtocolProtocolResolver, PKCS12ResourceProtocolProtocolResolver {

	private final Pattern pattern;

	protected AbstractKeyStoreProtocolResolver(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final Resource resolve(String location, ResourceLoader resourceLoader) {
		var matcher = pattern.matcher(location);

		if (matcher.find()) {
			try {
				var realLocation = matcher.group(1);
				var storepass = matcher.group(2);
				var in = resourceLoader.getResource(realLocation).getInputStream();
				return new PKCS12Resource(in, storepass);
			} catch (IOException e) {
				return null;
			}
		}

		return null;
	}

}
