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

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see ImportStringsCfg
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportStringsCfg.class)
@Repeatable(ImportStrings.class)
public @interface ImportString {

	/**
	 * Bean的名称
	 *
	 * @return Bean的名称
	 */
	String beanName() default "";

	/**
	 * Bean的Primary属性
	 *
	 * @return Bean的Primary属性
	 */
	boolean primary() default false;

	/**
	 * 资源位置
	 *
	 * @return 资源位置
	 */
	@AliasFor(attribute = "location")
	String value() default "";

	/**
	 * 资源位置
	 *
	 * @return 资源位置
	 */
	@AliasFor(attribute = "value")
	String location() default "";

	/**
	 * 编码
	 *
	 * @return 编码
	 */
	String charset() default "UTF-8";

	/**
	 * 是否需要trim
	 *
	 * @return 是否需要trim
	 */
	boolean trim() default false;

	/**
	 * 是否需要trim每一行
	 *
	 * @return 是否需要trim每一行
	 */
	boolean trimEachLine() default false;

}
