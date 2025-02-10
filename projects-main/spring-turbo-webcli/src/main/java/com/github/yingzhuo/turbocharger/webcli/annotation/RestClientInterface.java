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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 3.3.1
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestClientInterface {

	/**
	 * Bean Name
	 *
	 * @return Bean Name
	 * @see #beanName()
	 */
	@AliasFor("beanName")
	public String value() default "";

	/**
	 * Bean Name
	 *
	 * @return Bean Name
	 * @see #value()
	 */
	@AliasFor("value")
	public String beanName() default "";

	/**
	 * Bean qualifiers
	 *
	 * @return Bean qualifiers
	 * @see Qualifier
	 */
	public String[] qualifiers() default {};

	/**
	 * 是否为primary
	 *
	 * @return primary
	 * @see org.springframework.context.annotation.Primary
	 */
	public boolean primary() default false;

	/**
	 * 返回 {@link org.springframework.web.client.RestClient} 提供器类型
	 *
	 * @return {@link org.springframework.web.client.RestClient} 提供器类型
	 * @see RestClientSupplier
	 */
	public Class<? extends RestClientSupplier> clientSupplier() default RestClientSupplier.Default.class;

	public Class<? extends ArgumentResolversSupplier> argumentResolversSupplier() default ArgumentResolversSupplier.Default.class;

}
