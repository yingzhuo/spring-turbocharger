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
package com.github.yingzhuo.turbocharger.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

/**
 * @author 应卓
 * @since 3.5.0
 */
@Setter
public class HmacAlgFactoryBean implements FactoryBean<Algorithm> {

	private AlgorithmType algorithmType = AlgorithmType.HMAC_512;
	private String secret;

	/**
	 * 默认构造方法
	 */
	public HmacAlgFactoryBean() {
		super();
	}

	@Override
	public Algorithm getObject() {
		return switch (algorithmType) {
			case HMAC_256 -> Algorithm.HMAC256(secret);
			case HMAC_384 -> Algorithm.HMAC384(secret);
			case HMAC_512 -> Algorithm.HMAC512(secret);
			default -> throw new IllegalStateException("Unsupported algorithm type: " + algorithmType);
		};
	}

	@Nullable
	@Override
	public Class<?> getObjectType() {
		return Algorithm.class;
	}

}
