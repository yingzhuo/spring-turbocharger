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
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @see ImportKeyBundleFromStore
 * @since 3.5.3
 */
class ImportKeyBundleFromStoreCfg extends ImportBeanDefinitionRegistrarSupport {

	public ImportKeyBundleFromStoreCfg(ResourceLoader resourceLoader, Environment environment, BeanFactory beanFactory) {
		super(resourceLoader, environment, beanFactory);
	}

	@Override
	protected void doRegisterBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception {
		var attrs =
			getAnnotationAttributesSet(metadata, ImportKeyBundleFromStore.class, ImportKeyBundleFromStore.Container.class);

		for (var attr : attrs) {
			var beanName = attr.getString("beanName");

			var keyBundle = createKeyBundle(attr);

			var beanDef = BeanDefinitionBuilder.genericBeanDefinition(KeyBundle.class, () -> keyBundle)
				.setPrimary(attr.getBoolean("primary"))
				.setLazyInit(false)
				.setAbstract(false)
				.setScope(BeanDefinition.SCOPE_SINGLETON)
				.getBeanDefinition();

			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);

			for (var aliasOfBean : attr.getStringArray("aliasesOfBean")) {
				registry.registerAlias(aliasOfBean, beanName);
			}
		}
	}

	private KeyBundle createKeyBundle(AnnotationAttributes attr) throws Exception {
		var location = environment.resolvePlaceholders(attr.getString("location"));
		var type = (KeyStoreType) attr.getEnum("type");
		var storepass = environment.resolvePlaceholders(attr.getString("storepass"));
		var alias = environment.resolvePlaceholders(attr.getString("aliasOfStore"));
		var keypass = environment.resolvePlaceholders(StringUtils.hasText(attr.getString("keypass")) ? attr.getString("keypass") : storepass);

		var ks = KeyStoreUtils.loadKeyStore(resourceLoader.getResource(location).getInputStream(), type, storepass);

		return new KeyBundle() {
			@Override
			public <T extends PublicKey> T getPublicKey() {
				return KeyStoreUtils.getPublicKey(ks, alias);
			}

			@Override
			public <T extends PrivateKey> T getPrivateKey() {
				return KeyStoreUtils.getPrivateKey(ks, alias, keypass);
			}

			@Override
			public <T extends X509Certificate> T getCertificate() {
				return KeyStoreUtils.getCertificate(ks, alias);
			}

			@Override
			public List<X509Certificate> getCertificateChain() {
				return KeyStoreUtils.getCertificate(ks, alias);
			}

			@Override
			public String alias() {
				return alias;
			}
		};
	}

}
