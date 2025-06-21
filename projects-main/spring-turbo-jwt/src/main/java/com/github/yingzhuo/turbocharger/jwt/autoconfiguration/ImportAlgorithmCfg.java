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
import com.github.yingzhuo.turbocharger.bean.AbstractImportBeanDefinitionRegistrar;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.annotation.AnnotationAttributes;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @see ImportAlgorithm
 * @since 3.5.3
 */
class ImportAlgorithmCfg extends AbstractImportBeanDefinitionRegistrar {

	/**
	 * 默认构造方法
	 */
	public ImportAlgorithmCfg() {
		super(ImportAlgorithm.class);
		super.setIgnoreExceptions(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAnnotationAttributes(AnnotationAttributes attr, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
		var location = attr.getString("pemLocation");
		var keypass = attr.getString("keypass");
		var type = (ImportAlgorithm.AlgorithmType) attr.getEnum("type");
		var beanName = attr.getString("beanName");

		var beanDef =
			BeanDefinitionBuilder.genericBeanDefinition(Algorithm.class, getSupplier(location, keypass, type))
				.setScope(BeanDefinition.SCOPE_PROTOTYPE)
				.setAbstract(false)
				.setLazyInit(false)
				.setPrimary(false)
				.getBeanDefinition();

		if (beanName.isBlank()) {
			beanName = beanNameGenerator.generateBeanName(beanDef, registry);
		}

		registry.registerBeanDefinition(beanName, beanDef);
	}

	private Supplier<Algorithm> getSupplier(String location, String keypass, ImportAlgorithm.AlgorithmType type) {
		return () -> {
			var pc = PemContent.of(super.loadResourceAsString(location, StandardCharsets.UTF_8));
			var publicKey = pc.getCertificates().get(0).getPublicKey();
			var privateKey = pc.getPrivateKey(keypass);
			return switch (type) {
				case RSA256 -> Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case RSA384 -> Algorithm.RSA384((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case RSA512 -> Algorithm.RSA512((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
				case ECDSA256 -> Algorithm.ECDSA256((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
				case ECDSA384 -> Algorithm.ECDSA384((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
				case ECDSA512 -> Algorithm.ECDSA512((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
			};
		};
	}
}
