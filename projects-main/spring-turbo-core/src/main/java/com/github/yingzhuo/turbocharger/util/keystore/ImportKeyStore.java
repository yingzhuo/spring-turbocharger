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
package com.github.yingzhuo.turbocharger.util.keystore;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see java.security.KeyStore
 * @since 3.5.2
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ImportKeyStoreConfig.class)
@Repeatable(ImportKeyStore.RepeatableList.class)
public @interface ImportKeyStore {

	public String beanName();

	public String[] aliases() default {};

	public String location();

	public KeyStoreType type() default KeyStoreType.PKCS12;

	public String storepass();

	public boolean primary() default false;

	// -----------------------------------------------------------------------------------------------------------------

	@Inherited
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Import(ImportKeyStoreConfig.class)
	public static @interface RepeatableList {
		public ImportKeyStore[] value();
	}

}
