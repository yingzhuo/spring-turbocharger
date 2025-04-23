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

import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.id.UUIDs;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * (内部使用)
 *
 * @author 应卓
 * @since 2.1.3
 */
public abstract class AbstractPropertySourceFactory implements PropertySourceFactory {

	private final PropertySourceLoader loader;

	public AbstractPropertySourceFactory(PropertySourceLoader loader) {
		this.loader = loader;
	}

	@Override
	public final PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource)
		throws IOException {
		final var propertySourceName = enforcePropertySourceName(name, resource);
		final var list = loader.load(propertySourceName, resource.getResource());

		if (list.isEmpty()) {
			return EmptyPropertySource.of(propertySourceName);
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new IOException("multiple document is NOT supported yet.");
		}
	}

	private String enforcePropertySourceName(@Nullable String name, EncodedResource resource) {
		if (name == null) {
			name = resource.getResource().getFilename();
		}

		if (StringUtils.isBlank(name)) {
			return UUIDs.classic32();
		}
		return name;
	}

}
