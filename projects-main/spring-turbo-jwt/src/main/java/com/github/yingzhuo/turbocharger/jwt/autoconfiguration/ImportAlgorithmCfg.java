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
import com.github.yingzhuo.turbocharger.util.StringPool;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @see ImportAlgorithm
 * @since 3.5.3
 */
class ImportAlgorithmCfg extends ImportBeanDefinitionRegistrarSupport {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		for (var attr : getAnnotationAttributesSet(metadata, ImportAlgorithm.class)) {
			var location = attr.getString("pemLocation");
			var keypass = attr.getString("keypass");
			var kind = (ImportAlgorithm.AlgorithmKind) attr.getEnum("kind");
			var beanName = attr.getString("beanName");
			var primary = attr.getBoolean("primary");
			var scope = attr.getString("scope");

			var pemText = super.getResourceAsLines(location)
				.map(String::trim)
				.filter(StringUtils::hasText)
				.collect(Collectors.joining(StringPool.LF));

			var pc = PemContent.of(pemText);
			var publicKey = pc.getCertificates().get(0).getPublicKey();
			var privateKey = pc.getPrivateKey(keypass);

			var alg = switch (kind) {
				case RSA256 -> Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case RSA384 -> Algorithm.RSA384((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case RSA512 -> Algorithm.RSA512((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case ECDSA256 -> Algorithm.ECDSA256((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
				case ECDSA384 -> Algorithm.ECDSA384((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
				case ECDSA512 -> Algorithm.ECDSA512((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
			};

			var beanDef =
				BeanDefinitionBuilder.genericBeanDefinition(Algorithm.class, () -> alg)
					.setScope(scope)
					.setRole(BeanDefinition.ROLE_APPLICATION)
					.setAbstract(false)
					.setLazyInit(false)
					.setPrimary(primary)
					.getBeanDefinition();

			if (!StringUtils.hasText(beanName)) {
				beanName = beanNameGen.generateBeanName(beanDef, registry);
			}

			registry.registerBeanDefinition(beanName, beanDef);
		}
	}

}
