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
package com.github.yingzhuo.turbocharger.key.autoconfiguration;

import com.github.yingzhuo.turbocharger.bean.BeanInstanceSupplier;
import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.security.KeyStore;

/**
 * @author 应卓
 * @see ImportKeyStore
 * @since 3.5.3
 */
class ImportKeyStoreCfg extends ImportBeanDefinitionRegistrarSupport {

	public ImportKeyStoreCfg(ResourceLoader resourceLoader, Environment environment, BeanFactory beanFactory) {
		super(resourceLoader, environment, beanFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doRegisterBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		for (var attr : getAnnotationAttributesSet(metadata, ImportKeyStore.class, ImportKeyStore.Container.class)) {
			var location = attr.getString("location");
			var type = (KeyStoreType) attr.getEnum("type");
			var storepass = attr.getString("storepass");
			var beanName = attr.getString("beanName");
			var primary = attr.getBoolean("primary");

			var beanDef =
				BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class)
					.setPrimary(primary)
					.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
					.setAbstract(false)
					.setLazyInit(false)
					.getBeanDefinition();

			beanDef.setInstanceSupplier(new KeyStoreSupplier(resourceLoader, location, storepass, type));

			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);

			for (var alias : attr.getStringArray("aliasesOfBean")) {
				registry.registerAlias(beanName, alias);
			}
		}
	}

	private static class KeyStoreSupplier extends BeanInstanceSupplier<KeyStore> {
		private final ResourceLoader resourceLoader;
		private final String location;
		private final String storepass;
		private final KeyStoreType type;

		KeyStoreSupplier(ResourceLoader resourceLoader, String location, String storepass, KeyStoreType type) {
			this.resourceLoader = resourceLoader;
			this.location = location;
			this.storepass = storepass;
			this.type = type;
		}

		protected KeyStore doGet() throws Exception {
			return KeyStoreUtils.loadKeyStore(resourceLoader.getResource(location).getInputStream(), type, storepass);
		}
	}

}
