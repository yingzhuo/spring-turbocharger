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
package com.github.yingzhuo.turbocharger.openfeign;

import com.github.yingzhuo.turbocharger.bean.ImportBeanDefinitionRegistrarSupport;
import com.github.yingzhuo.turbocharger.bean.classpath.ClassPathScanner;
import com.github.yingzhuo.turbocharger.bean.classpath.PackageSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @author 应卓
 * @see EnableOpenFeign
 * @since 3.5.3
 */
class EnableOpenFeignCfg extends ImportBeanDefinitionRegistrarSupport {

	private static final Logger log = LoggerFactory.getLogger(EnableOpenFeignCfg.class);

	@Override
	protected void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception {
		var packageSet = createPackageSet(metadata);
		var scanner = createScanner();

		for (var genericBeanDefinition : scanner.scan(packageSet)) {
			// TODO:
			System.out.println(genericBeanDefinition.getBeanClassName());
		}
	}

	private PackageSet createPackageSet(AnnotationMetadata metadata) {
		var ps = new PackageSet();
		for (var attr : getAnnotationAttributesSet(metadata, EnableOpenFeign.class)) {
			var basePackages = attr.getStringArray("basePackages");
			ps.acceptPackages(basePackages);

			var basePackageClasses = attr.getClassArray("basePackageClasses");
			ps.acceptBasePackageClasses(basePackageClasses);
		}

		if (ps.isEmpty()) {
			ps.acceptPackages(getImportingClassPackage(metadata));
		}

		return ps;
	}

	private ClassPathScanner createScanner() {
		var scanner = new ClassPathScanner();
		scanner.addIncludeFilters(new OpenFeignCliTypeFilter());
		return scanner;
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static class OpenFeignCliTypeFilter implements TypeFilter {
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
			return metadataReader.getClassMetadata().isInterface() &&
				metadataReader.getAnnotationMetadata().hasAnnotation(OpenFeignClient.class.getName());
		}
	}

}
