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
package com.github.yingzhuo.turbocharger.jwt.autoconfiguration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 导入SM2签名算法
 *
 * @author 应卓
 * @see com.github.yingzhuo.turbocharger.jwt.algorithm.SM2Algorithm
 * @see cn.hutool.crypto.asymmetric.SM2
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportSM2AlgorithmCfg.class)
@Deprecated // 不易理解，使用起来有难度
public @interface ImportSM2Algorithm {

	/**
	 * Bean的名称
	 *
	 * @return Bean的名称
	 */
	String beanName() default "";

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
	 * 公钥字符串 (Hex或Base64)
	 *
	 * @return 公钥字符串
	 */
	String publicKeyText();

	/**
	 * 私钥字符串 (Hex或Base64)
	 *
	 * @return 公钥字符串
	 */
	String privateKeyText();

	/**
	 * SM2 - id
	 *
	 * @return SM2 - id
	 */
	String id() default "";

	/**
	 * SM2 - Mode
	 *
	 * @return SM2 - Mode
	 */
	Mode mode() default Mode.C1C3C2;

	// -----------------------------------------------------------------------------------------------------------------

	enum Mode {
		C1C2C3, C1C3C2
	}

}
