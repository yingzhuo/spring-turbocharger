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
package com.github.yingzhuo.turbocharger.core.resource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.zip.ZipFile;

/**
 * @author 应卓
 * @see ZipProtocolResolver
 * @see JarProtocolResolver
 * @since 3.3.5
 */
abstract class AbstractZipProtocolResolver implements ProtocolResolver {

	private final String prefix;

	public AbstractZipProtocolResolver(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final Resource resolve(String location, ResourceLoader resourceLoader) {
		if (StringUtils.startsWithIgnoreCase(location, prefix)) {

			var parts = StringUtils.delimitedListToStringArray(location.substring(prefix.length()), "!");
			if (parts.length == 2) {
				var zipLocation = parts[0];
				var entryName = parts[1];

				try {
					return doResolve(zipLocation, entryName, resourceLoader);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}
		}

		return null;
	}

	private Resource doResolve(String zipLocation, String entryName, ResourceLoader resourceLoader) throws IOException {
		var zipResource = resourceLoader.getResource(zipLocation);
		try (var zipFile = new ZipFile(zipResource.getFile())) {
			var zipEntry = zipFile.getEntry(entryName);
			return new ByteArrayResource(StreamUtils.copyToByteArray(zipFile.getInputStream(zipEntry)));
		}
	}

}
