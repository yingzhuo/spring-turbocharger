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

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ImportKeyBundleFromPemCfg.class)
@Repeatable(ImportKeyBundleFromPem.Container.class)
public @interface ImportKeyBundleFromPem {

	String beanName() default "";

	String[] aliasesOfBean() default {};

	boolean primary() default false;

	String scope() default BeanDefinition.SCOPE_SINGLETON;

	String location();

	String keypass() default "";

	// -----------------------------------------------------------------------------------------------------------------

	@Inherited
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Import(ImportKeyBundleFromPemCfg.class)
	@interface Container {
		ImportKeyBundleFromPem[] value();
	}

}
