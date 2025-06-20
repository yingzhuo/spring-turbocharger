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
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 3.5.2
 */
class ImportKeyStoreConfig implements ImportBeanDefinitionRegistrar {

	private final ResourceLoader resourceLoader;
	private final List<AnnotationAttributes> importingAttributes = new ArrayList<>();

	public ImportKeyStoreConfig(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		setupImportingAttributes(metadata);

		for (var attr : importingAttributes) {
			KeyStoreType keyStoreType = attr.getEnum("type");
			String location = attr.getString("location");
			String storepass = attr.getString("storepass");
			String beanName = attr.getString("beanName");

			var beanDef =
				BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class, getSupplier(keyStoreType, location, storepass))
					.setPrimary(attr.getBoolean("primary"))
					.setScope(BeanDefinition.SCOPE_SINGLETON)
					.setAbstract(false)
					.setLazyInit(false)
					.getBeanDefinition();

			registry.registerBeanDefinition(beanName, beanDef);

			Stream.of(attr.getStringArray("aliases"))
				.forEach(alias -> registry.registerAlias(beanName, alias));
		}
	}

	private void setupImportingAttributes(AnnotationMetadata metadata) {
		var singleAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ImportKeyStore.class.getName()));
		Optional.ofNullable(singleAttributes).ifPresent(it -> importingAttributes.add(singleAttributes));

		var listAttributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ImportKeyStore.RepeatableList.class.getName()));
		if (listAttributes != null) {
			Collections.addAll(importingAttributes, listAttributes.getAnnotationArray("value"));
		}
	}

	private Supplier<KeyStore> getSupplier(KeyStoreType type, String location, String storepass) {
		return () -> {
			var resource = resourceLoader.getResource(location);
			try {
				return KeyStoreUtils.loadKeyStore(resource.getInputStream(), type, storepass);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		};
	}

}
