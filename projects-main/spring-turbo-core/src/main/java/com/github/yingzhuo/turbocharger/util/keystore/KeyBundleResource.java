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

import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @see KeyBundle
 * @see #loadKeyBundle()
 * @since 3.5.0
 */
public class KeyBundleResource implements Resource {

	@NonNull
	private final Resource delegatingResource;

	@Nullable
	private final String keypass;

	public KeyBundleResource(Resource delegatingResource, @Nullable String keypass) {
		this.delegatingResource = delegatingResource;
		this.keypass = keypass;
	}

	public KeyBundle loadKeyBundle() {
		try {
			return KeyBundleFactories.ofPemContent(getContentAsString(UTF_8), keypass);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		return delegatingResource.exists();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URL getURL() throws IOException {
		return delegatingResource.getURL();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI getURI() throws IOException {
		return delegatingResource.getURI();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getFile() throws IOException {
		return delegatingResource.getFile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long contentLength() throws IOException {
		return delegatingResource.contentLength();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long lastModified() throws IOException {
		return delegatingResource.lastModified();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Resource createRelative(String relativePath) throws IOException {
		return delegatingResource.createRelative(relativePath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public String getFilename() {
		return delegatingResource.getFilename();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return delegatingResource.getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return delegatingResource.getInputStream();
	}
}
