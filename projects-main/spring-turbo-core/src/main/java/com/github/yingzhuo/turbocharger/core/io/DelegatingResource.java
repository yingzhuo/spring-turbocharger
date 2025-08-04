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
package com.github.yingzhuo.turbocharger.core.io;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 应卓
 * @since 3.5.4
 */
public abstract class DelegatingResource extends AbstractResource {

	private final Resource delegatingResource;

	protected DelegatingResource(Resource delegatingResource) {
		Assert.notNull(delegatingResource, "delegatingResource may not be null");
		this.delegatingResource = delegatingResource;
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

	public Resource getDelegatingResource() {
		return this.delegatingResource;
	}

}
