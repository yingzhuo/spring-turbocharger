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
package com.github.yingzhuo.turbocharger.bean.generic;

import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.Collectors;

/**
 * @author 应卓
 * @see ImportStringBean
 * @see ImportStringBeans
 * @since 3.5.3
 */
class ImportStringBeansCfg extends ImportBeanDefinitionRegistrarSupport implements ImportBeanDefinitionRegistrar {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		getAnnotationAttributesSet(metadata, ImportStringBean.class, ImportStringBeans.class)
			.forEach(attr -> {
				var beanName = attr.getString("beanName");
				if (beanName.isBlank()) {
					throw new IllegalArgumentException("beanName is blank");
				}

				var location = attr.getString("location");
				if (location.isBlank()) {
					throw new IllegalArgumentException("location is blank");
				}

				var charset = attr.getString("charset");
				var trim = attr.getBoolean("trim");
				var trimEachLine = attr.getBoolean("trimEachLine");

				var text = super.getResourceAsString(location, charset);

				if (trim) {
					text = text.trim();
				}

				if (trimEachLine) {
					text = text.lines()
						.map(String::trim)
						.collect(Collectors.joining(System.lineSeparator()));
				}

				var beanDef = BeanDefinitionBuilder.genericBeanDefinition(String.class)
					.setPrimary(false)
					.setAbstract(false)
					.setLazyInit(false)
					.setScope(AbstractBeanDefinition.SCOPE_SINGLETON)
					.setRole(AbstractBeanDefinition.ROLE_APPLICATION)
					.getBeanDefinition();

				final var s = text;
				beanDef.setInstanceSupplier(() -> s);

				registry.registerBeanDefinition(beanName, beanDef);
			});
	}

}
