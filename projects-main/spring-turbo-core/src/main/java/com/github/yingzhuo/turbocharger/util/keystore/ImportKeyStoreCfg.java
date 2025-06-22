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

import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyStore;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @see ImportKeyStore
 * @since 3.5.2
 */
class ImportKeyStoreCfg extends ImportBeanDefinitionRegistrarSupport {

	private final ResourceLoader resourceLoader;

	public ImportKeyStoreCfg(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
		for (var attr : getAnnotationAttributesList(metadata, ImportKeyStore.class, ImportKeyStore.RepeatableList.class)) {
			var location = attr.getString("location");
			var type = (KeyStoreType) attr.getEnum("type");
			var storepass = attr.getString("storepass");
			var beanName = attr.getString("beanName");
			var isPrimary = attr.getBoolean("primary");

			var beanDef =
				BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class)
					.setPrimary(isPrimary)
					.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
					.setAbstract(false)
					.setLazyInit(false)
					.getBeanDefinition();

			beanDef.setInstanceSupplier(new KeyStoreSupplier(resourceLoader, location, storepass, type));

			registry.registerBeanDefinition(beanName, beanDef);

			for (var alias : attr.getStringArray("aliases")) {
				registry.registerAlias(beanName, alias);
			}
		}
	}

	private record KeyStoreSupplier(
		ResourceLoader resourceLoader,
		String location,
		String storepass,
		KeyStoreType type) implements Supplier<KeyStore> {

		@Override
		public KeyStore get() {
			try {
				return doGet();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		private KeyStore doGet() throws IOException {
			return KeyStoreUtils.loadKeyStore(resourceLoader.getResource(location).getInputStream(), type, storepass);
		}
	}

}
