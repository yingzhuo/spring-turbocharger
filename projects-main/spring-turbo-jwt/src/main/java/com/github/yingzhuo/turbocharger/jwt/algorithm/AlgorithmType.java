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

/**
 * @author 应卓
 * @since 3.5.0
 */
public enum AlgorithmType {

	/**
	 * @see EcdsaPemAlgorithmFactoryBean
	 * @see EcdsaStoreAlgorithmFactoryBean
	 */
	ECDSA256,

	/**
	 * @see EcdsaPemAlgorithmFactoryBean
	 * @see EcdsaStoreAlgorithmFactoryBean
	 */
	ECDSA384,

	/**
	 * @see EcdsaPemAlgorithmFactoryBean
	 * @see EcdsaStoreAlgorithmFactoryBean
	 */
	ECDSA512,

	/**
	 * @see RsaPemAlgorithmFactoryBean
	 * @see RsaStoreAlgorithmFactoryBean
	 */
	RSA256,

	/**
	 * @see RsaPemAlgorithmFactoryBean
	 * @see RsaStoreAlgorithmFactoryBean
	 */
	RSA384,

	/**
	 * @see RsaPemAlgorithmFactoryBean
	 * @see RsaStoreAlgorithmFactoryBean
	 */
	RSA512,

	/**
	 * @see HmacStoreAlgorithmFactoryBean
	 */
	HMAC256,

	/**
	 * @see HmacStoreAlgorithmFactoryBean
	 */
	HMAC384,

	/**
	 * @see HmacStoreAlgorithmFactoryBean
	 */
	HMAC512

}
