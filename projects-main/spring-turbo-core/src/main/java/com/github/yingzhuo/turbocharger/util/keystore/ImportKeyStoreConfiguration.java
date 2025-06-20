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

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyStore;

/**
 * @author 应卓
 * @since 3.5.2
 */
class ImportKeyStoreConfiguration implements ImportBeanDefinitionRegistrar {

	private final ResourceLoader resourceLoader;

	public ImportKeyStoreConfiguration(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {

		var attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ImportKeyStore.class.getName()));

		if (attributes == null || attributes.isEmpty()) {
			return;
		}

		KeyStoreType type = attributes.getEnum("type");
		String location = attributes.getString("location");
		String storepass = attributes.getString("storepass");
		String beanName = attributes.getString("beanName");

		var keyStore = loadKeyStore(type, location, storepass);

		var beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class, () -> keyStore)
			.setScope(BeanDefinition.SCOPE_SINGLETON)
			.setPrimary(attributes.getBoolean("primary"))
			.getBeanDefinition();

		if (!StringUtils.hasText(beanName)) {
			beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
		}

		registry.registerBeanDefinition(beanName, beanDefinition);
	}

	private KeyStore loadKeyStore(KeyStoreType keyStoreType, String location, String storepass) {
		var resource = resourceLoader.getResource(location);
		try {
			return KeyStoreUtils.loadKeyStore(resource.getInputStream(), keyStoreType, storepass);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
