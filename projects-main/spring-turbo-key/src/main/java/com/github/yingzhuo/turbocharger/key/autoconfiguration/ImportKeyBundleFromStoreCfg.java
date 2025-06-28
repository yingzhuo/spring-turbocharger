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

import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import com.github.yingzhuo.turbocharger.key.KeyBundle;
import com.github.yingzhuo.turbocharger.key.SimpleKeyBundle;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @see ImportKeyBundleFromStore
 * @since 3.5.3
 */
class ImportKeyBundleFromStoreCfg extends ImportBeanDefinitionRegistrarSupport {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		var attrs =
			getAnnotationAttributesSet(metadata, ImportKeyBundleFromStore.class, ImportKeyBundleFromStore.Container.class);

		for (var attr : attrs) {
			var beanName = attr.getString("beanName");

			var keyBundle = createKeyBundle(attr);

			var beanDef = BeanDefinitionBuilder.genericBeanDefinition(KeyBundle.class, () -> keyBundle)
				.setPrimary(attr.getBoolean("primary"))
				.setLazyInit(false)
				.setAbstract(false)
				.setScope(attr.getString("scope"))
				.getBeanDefinition();

			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);
		}
	}

	private KeyBundle createKeyBundle(AnnotationAttributes attr) {
		var location = attr.getString("location");
		var type = (KeyStoreType) attr.getEnum("type");
		var storepass = attr.getString("storepass");
		var alias = attr.getString("aliasOfStore");
		var keypass = StringUtils.hasText(attr.getString("keypass")) ? attr.getString("keypass") : storepass;

		var ks = KeyStoreUtils.loadKeyStore(getResourceAsInputStream(location), type, storepass);

		return new SimpleKeyBundle(
			alias,
			KeyStoreUtils.getCertificate(ks, alias),
			KeyStoreUtils.getPrivateKey(ks, alias, keypass)
		);
	}

}
