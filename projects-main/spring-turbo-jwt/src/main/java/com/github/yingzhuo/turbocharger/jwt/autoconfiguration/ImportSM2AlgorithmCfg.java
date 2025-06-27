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
package com.github.yingzhuo.turbocharger.jwt.autoconfiguration;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import com.github.yingzhuo.turbocharger.jwt.algorithm.SM2Algorithm;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @since 3.5.3
 */
class ImportSM2AlgorithmCfg extends ImportBeanDefinitionRegistrarSupport {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {

		for (var attr : getAnnotationAttributesSet(metadata, ImportSM2Algorithm.class)) {

			var publicKey = getEnvironment().resolvePlaceholders(attr.getString("publicKeyText"));
			var privateKey = getEnvironment().resolvePlaceholders(attr.getString("privateKeyText"));
			var id = getEnvironment().resolvePlaceholders(attr.getString("id"));

			if (id.isEmpty()) {
				id = null;
			}

			var alg = new SM2Algorithm(publicKey, privateKey, id);
			SM2Engine.Mode mode = SM2Engine.Mode.valueOf(attr.getEnum("mode").toString());
			alg.setMode(mode);

			var beanDef = BeanDefinitionBuilder.genericBeanDefinition(Algorithm.class, () -> alg)
				.setScope(attr.getString("scope"))
				.setPrimary(attr.getBoolean("primary"))
				.getBeanDefinition();

			var beanName = attr.getString("beanName");
			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);

			for (var alias : attr.getStringArray("aliasesOfBean")) {
				registry.registerAlias(alias, beanName);
			}
		}
	}
}
