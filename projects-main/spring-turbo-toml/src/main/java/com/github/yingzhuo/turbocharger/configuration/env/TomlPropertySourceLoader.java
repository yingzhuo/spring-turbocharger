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

import com.moandjiezana.toml.Toml;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 应卓
 * @since 3.4.5
 */
public class TomlPropertySourceLoader implements PropertySourceLoader {

	@Override
	public String[] getFileExtensions() {
		return new String[]{"toml"};
	}

	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		try (InputStream in = resource.getInputStream()) {
			Toml toml = new Toml().read(in);
			Map<String, Object> source = toml.toMap();

			Map<String, Object> result = new LinkedHashMap<>();
			buildFlattenedMap(result, source, null);
			return Collections.singletonList(new MapPropertySource(name, result));
		}
	}

	private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String root) {
		boolean rootHasText = (null != root && !root.trim().isEmpty());

		source.forEach((key, value) -> {
			String path;

			if (rootHasText) {
				if (key.startsWith("[")) {
					path = root + key;
				} else {
					path = root + "." + key;
				}
			} else {
				path = key;
			}

			if (value instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) value;
				buildFlattenedMap(result, map, path);
			} else if (value instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) value;
				int count = 0;
				for (Object object : collection) {
					buildFlattenedMap(result, Collections.singletonMap("[" + (count++) + "]", object), path);
				}
			} else {
				result.put(path, null == value ? "" : value);
			}
		});
	}
}
