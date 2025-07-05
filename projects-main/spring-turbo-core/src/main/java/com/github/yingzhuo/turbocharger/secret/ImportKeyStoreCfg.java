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
package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.KeyStore;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @see ImportKeyStore
 * @since 3.5.3
 */
class ImportKeyStoreCfg extends ImportBeanDefinitionRegistrarSupport {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		for (var attr : getAnnotationAttributesSet(metadata, ImportKeyStore.class, ImportKeyStore.Container.class)) {
			var location = attr.getString("location");
			var type = (KeyStoreType) attr.getEnum("type");
			var storepass = attr.getString("storepass");
			var beanName = attr.getString("beanName");
			var primary = attr.getBoolean("primary");

			var beanDef =
				BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class)
					.setPrimary(primary)
					.setRole(BeanDefinition.ROLE_APPLICATION)
					.setScope(attr.getString("scope"))
					.setAbstract(false)
					.setLazyInit(false)
					.getBeanDefinition();

			beanDef.setInstanceSupplier(new KeyStoreSupplier(getResourceLoader(), location, storepass, type));

			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);
		}
	}

	private record KeyStoreSupplier(
		ResourceLoader resourceLoader,
		String location, String storepass,
		KeyStoreType type) implements Supplier<KeyStore> {

		@Override
		public KeyStore get() {
			try {
				return KeyStoreUtils.loadKeyStore(resourceLoader.getResource(location).getInputStream(), type, storepass);
			} catch (IOException e) {
				throw new BeanCreationException(e.getMessage(), e);
			}
		}
	}

}
