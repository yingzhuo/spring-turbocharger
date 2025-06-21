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

import org.springframework.context.annotation.Import;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * @author 应卓
 * @see ImportAlgorithmCfg
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ImportAlgorithmCfg.class)
public @interface ImportAlgorithm {

	public String pemLocation();

	public String keypass() default "";

	public AlgorithmKind kind();

	public String beanName() default "";

	public String[] aliases() default {};

	public boolean primary() default false;

	// -----------------------------------------------------------------------------------------------------------------

	enum AlgorithmKind implements Serializable {
		RSA256, RSA384, RSA512, ECDSA256, ECDSA384, ECDSA512
	}

}
