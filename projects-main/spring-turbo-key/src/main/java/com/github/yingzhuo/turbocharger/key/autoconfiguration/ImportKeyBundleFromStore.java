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

import com.github.yingzhuo.turbocharger.key.KeyBundle;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
import java.security.KeyStore;

/**
 * 通过{@link KeyStore} 在 {@link ApplicationContext} 中导入 {@link KeyBundle} 实例
 *
 * @author 应卓
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportKeyBundleFromStoreCfg.class)
@Repeatable(ImportKeyBundleFromStore.Container.class)
public @interface ImportKeyBundleFromStore {

	/**
	 * Bean的名称
	 *
	 * @return Bean的名称
	 */
	String beanName() default "";

	/**
	 * Bean的别名 (多个)
	 *
	 * @return Bean的别名
	 */
	String[] aliasesOfBean() default {};

	/**
	 * 是否为primary属性的Bean
	 *
	 * @return 是否为primary属性的Bean
	 */
	boolean primary() default false;

	/**
	 * Bean的Scope
	 *
	 * @return bean的Scope
	 * @see BeanDefinition#SCOPE_SINGLETON
	 * @see BeanDefinition#SCOPE_PROTOTYPE
	 */
	String scope() default BeanDefinition.SCOPE_SINGLETON;

	/**
	 * 资源文件位置
	 *
	 * @return 资源文件位置
	 */
	String location();

	/**
	 * {@link KeyStore} 格式
	 *
	 * @see KeyStore#getDefaultType()
	 * @return {@link KeyStore} 格式
	 */
	KeyStoreType type() default KeyStoreType.PKCS12;

	/**
	 * 库密码
	 *
	 * @return 库密码
	 */
	String storepass();

	/**
	 * 库中条目别名
	 *
	 * @return 库中条目别名
	 */
	String aliasOfStore();

	/**
	 * 私钥密码
	 *
	 * @return 私钥密码
	 */
	String keypass() default "";

	// -----------------------------------------------------------------------------------------------------------------

	@Inherited
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Import(ImportKeyBundleFromStoreCfg.class)
	@interface Container {
		ImportKeyBundleFromStore[] value();
	}

}
