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
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see ImportKeyStore
 * @see ImportKeyStoreCfg
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ImportKeyStore(location = "file:${user.home}/.keystore", storepass = "changeit")
public @interface ImportUserDefaultKeyStore {

	@AliasFor(annotation = ImportKeyStore.class, attribute = "beanName")
	String beanName() default "";

	@AliasFor(annotation = ImportKeyStore.class, attribute = "primary")
	boolean primary() default false;

	@AliasFor(annotation = ImportKeyStore.class, attribute = "scope")
	String scope() default BeanDefinition.SCOPE_SINGLETON;

	@AliasFor(annotation = ImportKeyStore.class, attribute = "type")
	KeyStoreType type() default KeyStoreType.PKCS12;

	@AliasFor(annotation = ImportKeyStore.class, attribute = "storepass")
	String storepass();

}
