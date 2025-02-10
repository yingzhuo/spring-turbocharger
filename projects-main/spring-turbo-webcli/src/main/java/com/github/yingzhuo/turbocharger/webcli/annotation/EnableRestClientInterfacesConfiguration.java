/*
 *
 * Copyright 2022-2025 the original author or authors.
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

package com.github.yingzhuo.turbocharger.webcli.annotation;

import com.github.yingzhuo.turbocharger.bean.classpath.ClassDefinition;
import com.github.yingzhuo.turbocharger.bean.classpath.ClassPathScanner;
import com.github.yingzhuo.turbocharger.bean.classpath.PackageSet;
import com.github.yingzhuo.turbocharger.util.ClassUtils;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.reflection.InstanceUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import static com.github.yingzhuo.turbocharger.bean.classpath.TypeFilterFactories.*;

/**
 * @author 应卓
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
class EnableRestClientInterfacesConfiguration implements ImportBeanDefinitionRegistrar {

	private static final Class<EnableRestClientInterfaces> IMPORTING_ANNOTATION_CLASS = EnableRestClientInterfaces.class;

	private final ClassLoader classLoader;
	private final Environment environment;
	private final ResourceLoader resourceLoader;

	public EnableRestClientInterfacesConfiguration(ClassLoader classLoader, Environment environment, ResourceLoader resourceLoader) {
		this.classLoader = classLoader;
		this.environment = environment;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator ng) {
		var importingAnnotationAttributes =
			AnnotationAttributes.fromMap(
				importingClassMetadata.getAnnotationAttributes(IMPORTING_ANNOTATION_CLASS.getName(), false)
			);

		if (importingAnnotationAttributes == null) {
			// 这种情况实际不会发生
			return;
		}

		var packageSet = PackageSet.newInstance()
			.acceptPackages(importingAnnotationAttributes.getStringArray("basePackages"))
			.acceptBaseClasses(importingAnnotationAttributes.getClassArray("basePackageClasses"));

		if (packageSet.isEmpty()) {
			packageSet.acceptPackages(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
		}

		var classDefs = ClassPathScanner.builder()
			.classLoader(classLoader)
			.resourceLoader(resourceLoader)
			.environment(environment)
			.includeFilter(all(
				isInterface(),
				hasAnnotation(RestClientInterface.class)
			))
			.build()
			.scan(packageSet);

		if (!classDefs.isEmpty()) {
			var globalArgumentResolversSupplierClass =
				(Class<? extends ArgumentResolversSupplier>) importingAnnotationAttributes.getClass("globalArgumentResolversSupplier");

			var globalArgumentResolversSupplier = InstanceUtils.newInstanceElseThrow(globalArgumentResolversSupplierClass);

			for (var classDef : classDefs) {
				registerOne(registry, ng, classDef, globalArgumentResolversSupplier);
			}
		}
	}

	private void registerOne(BeanDefinitionRegistry registry, BeanNameGenerator nameGen, ClassDefinition classDef, ArgumentResolversSupplier globalArgumentResolversSupplier) {

		var metaAnnotation = classDef.getRequiredAnnotation(RestClientInterface.class);
		var clientSupplier = InstanceUtils.newInstanceElseThrow(metaAnnotation.clientSupplier());
		var argumentResolversSupplier = InstanceUtils.newInstanceElseThrow(metaAnnotation.argumentResolversSupplier());

		var interfaceGen = new RestClientInterfaceGen(
			classDef,
			environment,
			clientSupplier,
			globalArgumentResolversSupplier,
			argumentResolversSupplier
		);

		var beanType = classDef.getBeanClass();
		var clientBeanDefinition =
			BeanDefinitionBuilder.genericBeanDefinition(beanType, interfaceGen)
				.setPrimary(metaAnnotation.primary())
				.setLazyInit(classDef.isLazyInit())
				.setAbstract(false)
				.setRole(classDef.getRole())
				.getBeanDefinition();

		addQualifiers(clientBeanDefinition, metaAnnotation.qualifiers());

		var beanName = metaAnnotation.value();

		if (beanName.isBlank()) {
			beanName = nameGen.generateBeanName(clientBeanDefinition, registry);
		}

		registry.registerBeanDefinition(beanName, clientBeanDefinition);
	}

	private void addQualifiers(AbstractBeanDefinition beanDefinition, String... qualifiers) {
		for (var qualifier : qualifiers) {
			if (StringUtils.isNotBlank(qualifier)) {
				beanDefinition.addQualifier(new AutowireCandidateQualifier(Qualifier.class, qualifier));
			}
		}
	}

}
