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

/**
 * @author 应卓
 * @since 3.5.0
 * @deprecated 使用 {@link HmacStoreAlgorithmFactoryBean} 替代
 */
@Deprecated(since = "3.5.0", forRemoval = true)
public class HmacSimpleAlgorithmFactoryBean implements FactoryBean<Algorithm> {

	@Setter
	private AlgorithmType algorithmType = AlgorithmType.HMAC512;

	@Setter
	private String secret;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Algorithm getObject() {
		return switch (algorithmType) {
			case HMAC256 -> Algorithm.HMAC256(secret);
			case HMAC384 -> Algorithm.HMAC384(secret);
			case HMAC512 -> Algorithm.HMAC512(secret);
			default -> throw new UnsupportedAlgorithmTypeException("Unsupported algorithm type: " + algorithmType);
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return Algorithm.class;
	}

}
