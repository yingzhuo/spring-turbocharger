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
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

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
@Target(ElementType.TYPE)
@Import(ImportAlgorithmCfg.class)
public @interface ImportAlgorithm {

	/**
	 * pem资源位置
	 *
	 * @return pem资源位置
	 */
	public String pemLocation();

	/**
	 * 私钥密码
	 *
	 * @return 私钥密码
	 */
	public String keypass() default "";

	/**
	 * 算法类型
	 *
	 * @return 算法类型
	 */
	public AlgorithmKind kind();

	/**
	 * Bean的名称
	 *
	 * @return Bean的名称
	 */
	public String beanName() default "";

	/**
	 * Bean的别名
	 *
	 * @return Bean的别名
	 */
	public String[] aliases() default {};

}
