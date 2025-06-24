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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.3
 */
@SuppressWarnings("unchecked")
class ImportKeyBundleFromPemCfg extends ImportBeanDefinitionRegistrarSupport {

	public ImportKeyBundleFromPemCfg(ResourceLoader resourceLoader, Environment environment) {
		super(resourceLoader, environment);
	}

	@Override
	protected void doRegisterBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception {
		var attrs =
			getAnnotationAttributesSet(metadata, ImportKeyBundleFromPem.class, ImportKeyBundleFromPem.Container.class);

		for (var attr : attrs) {
			var keyBundle = createKeyBundle(attr);

			var beanDef = BeanDefinitionBuilder.genericBeanDefinition(KeyBundle.class, () -> keyBundle)
				.setPrimary(attr.getBoolean("primary"))
				.setLazyInit(false)
				.setAbstract(false)
				.setScope(BeanDefinition.SCOPE_SINGLETON)
				.getBeanDefinition();

			var beanName = attr.getString("beanName");
			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);

			for (String alias : attr.getStringArray("aliasesOfBean")) {
				registry.registerAlias(alias, beanName);
			}
		}
	}

	private KeyBundle createKeyBundle(AnnotationAttributes attr) throws Exception {
		final var location = attr.getString("location");
		final var keypass = attr.getString("keypass");

		final var pemContent =
			PemContent.of(resourceLoader.getResource(location).getContentAsString(StandardCharsets.UTF_8));

		return new KeyBundle() {

			@Override
			public <T extends PublicKey> T getPublicKey() {
				return (T) getCertificate().getPublicKey();
			}

			@Override
			public <T extends PrivateKey> T getPrivateKey() {
				return (T) pemContent.getPrivateKey(keypass);
			}

			@Override
			public <T extends X509Certificate> T getCertificate() {
				return (T) getCertificateChain().get(0);
			}

			@Override
			public List<X509Certificate> getCertificateChain() {
				return pemContent.getCertificates();
			}
		};
	}
}
