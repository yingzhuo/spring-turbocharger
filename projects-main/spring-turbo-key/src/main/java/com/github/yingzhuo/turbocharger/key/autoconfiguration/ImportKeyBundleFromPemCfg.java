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
import com.github.yingzhuo.turbocharger.util.StringPool;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * @author 应卓
 * @see ImportKeyBundleFromPem
 * @since 3.5.3
 */
class ImportKeyBundleFromPemCfg extends ImportBeanDefinitionRegistrarSupport {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		var attrs =
			getAnnotationAttributesSet(metadata, ImportKeyBundleFromPem.class, ImportKeyBundleFromPem.Container.class);

		for (var attr : attrs) {
			var keyBundle = createKeyBundle(attr);

			var beanDef = BeanDefinitionBuilder.genericBeanDefinition(KeyBundle.class, () -> keyBundle)
				.setPrimary(attr.getBoolean("primary"))
				.setLazyInit(false)
				.setAbstract(false)
				.setScope(attr.getString("scope"))
				.getBeanDefinition();

			var beanName = attr.getString("beanName");
			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);
		}
	}

	private KeyBundle createKeyBundle(AnnotationAttributes attr) {
		var location = attr.getString("location");
		var keypass = attr.getString("keypass");

		var pemText = getResourceAsLines(location)
				.map(String::trim)
				.filter(StringUtils::hasText)
				.collect(Collectors.joining(StringPool.LF));

		var pemContent = PemContent.of(pemText);

		return new SimpleKeyBundle(
			null,
			pemContent.getCertificates().get(0),
			pemContent.getPrivateKey(keypass)
		);
	}
}
