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

import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author 应卓
 * @see java.security.KeyStore
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@Import(ImportKeyStoreCfg.class)
@Repeatable(ImportKeyStore.Container.class)
public @interface ImportKeyStore {

	String beanName() default "";

	boolean primary() default false;

	String scope() default BeanDefinition.SCOPE_SINGLETON;

	String location();

	KeyStoreType type() default KeyStoreType.PKCS12;

	String storepass();

	// -----------------------------------------------------------------------------------------------------------------

	@Inherited
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({TYPE, ANNOTATION_TYPE})
	@Import(ImportKeyStoreCfg.class)
	@interface Container {
		public ImportKeyStore[] value();
	}

}
