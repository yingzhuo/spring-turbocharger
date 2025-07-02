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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 3.5.3
 */
public class GenericAlgorithmFactoryBean implements FactoryBean<GenericAlgorithm>, ResourceLoaderAware {

	private ResourceLoader resourceLoader;
	private String pemLocation;
	private String password;

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
		Assert.notNull(password, "password must not be null");

		var pem = resourceLoader.getResource(pemLocation).getContentAsString(StandardCharsets.UTF_8);
		return new GenericAlgorithm(pem, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return GenericAlgorithm.class;
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

}
