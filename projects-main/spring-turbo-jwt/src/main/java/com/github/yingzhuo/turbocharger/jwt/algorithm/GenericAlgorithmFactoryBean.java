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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @see GenericAlgorithm
 * @since 3.5.3
 */
public class GenericAlgorithmFactoryBean implements FactoryBean<GenericAlgorithm>, ResourceLoaderAware {

	private @Nullable ResourceLoader resourceLoader;
	private @Nullable String pemLocation;
	private @Nullable String password;
	private @NonNull Charset pemCharset = StandardCharsets.UTF_8;

	/**
	 * 默认构造方法
	 */
	public GenericAlgorithmFactoryBean() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericAlgorithm getObject() throws Exception {
		Assert.notNull(resourceLoader, "resourceLoader must not be null");
		Assert.hasText(pemLocation, "pemLocation must not be blank");
		Assert.notNull(pemCharset, "pemCharset must not be null");

		var resource = resourceLoader.getResource(pemLocation);
		return new GenericAlgorithm(resource.getContentAsString(pemCharset), password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return Algorithm.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setPemLocation(String pemLocation) {
		this.pemLocation = pemLocation;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPemCharset(Charset pemCharset) {
		this.pemCharset = pemCharset;
	}
}
