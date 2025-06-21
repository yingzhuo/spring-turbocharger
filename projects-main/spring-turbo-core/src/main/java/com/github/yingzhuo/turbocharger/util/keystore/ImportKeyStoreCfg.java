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

import com.github.yingzhuo.turbocharger.bean.AbstractImportBeanDefinitionRegistrar;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;

import java.security.KeyStore;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @see ImportKeyStore
 * @since 3.5.2
 */
class ImportKeyStoreCfg extends AbstractImportBeanDefinitionRegistrar {

	/**
	 * 默认构造方法
	 */
	public ImportKeyStoreCfg() {
		super(ImportKeyStore.class, ImportKeyStore.RepeatableList.class);
		super.setIgnoreExceptions(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAnnotationAttributes(AnnotationAttributes attr, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
		var keyStoreType = (KeyStoreType) attr.getEnum("type");
		var location = attr.getString("location");
		var storepass = attr.getString("storepass");
		var beanName = attr.getString("beanName");
		var isPrimary = attr.getBoolean("primary");

		var beanDef =
			BeanDefinitionBuilder.genericBeanDefinition(KeyStore.class, getSupplier(keyStoreType, location, storepass))
				.setPrimary(isPrimary)
				.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON)
				.setAbstract(false)
				.setLazyInit(false)
				.getBeanDefinition();

		registry.registerBeanDefinition(beanName, beanDef);

		for (var alias : attr.getStringArray("aliases")) {
			registry.registerAlias(beanName, alias);
		}
	}

	private Supplier<KeyStore> getSupplier(KeyStoreType type, String location, String storepass) {
		return () -> KeyStoreUtils.loadKeyStore(super.loadResourceAsStream(location), type, storepass);
	}

}
