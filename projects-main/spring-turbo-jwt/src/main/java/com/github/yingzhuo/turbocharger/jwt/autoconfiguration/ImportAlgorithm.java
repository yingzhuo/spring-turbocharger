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

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 通过PEM文件导入{@link Algorithm}实例
 *
 * @author 应卓
 * @see ImportAlgorithmCfg
 * @see Algorithm
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@Import(ImportAlgorithmCfg.class)
public @interface ImportAlgorithm {

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
	 * pem资源位置
	 *
	 * @return pem资源位置
	 */
	String pemLocation();

	/**
	 * 私钥密码
	 *
	 * @return 私钥密码
	 */
	String keypass() default "";

	/**
	 * 算法类型
	 *
	 * @return 算法类型
	 */
	AlgorithmKind kind();

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * @author 应卓
	 * @since 3.5.3
	 */
	enum AlgorithmKind {
		RSA256, RSA384, RSA512, ECDSA256, ECDSA384, ECDSA512;
	}
}
