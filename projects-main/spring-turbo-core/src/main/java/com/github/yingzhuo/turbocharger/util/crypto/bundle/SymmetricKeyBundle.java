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
package com.github.yingzhuo.turbocharger.util.crypto.bundle;

import java.io.Serializable;
import java.security.Key;

/**
 * 对秘钥的封装
 *
 * @author 应卓
 * @see KeyStoreSymmetricKeyBundleFactoryBean
 * @since 3.3.1
 */
@FunctionalInterface
public interface SymmetricKeyBundle extends Serializable {

	/**
	 * 获取秘钥
	 *
	 * @param <T> 秘钥类型泛型
	 * @return 秘钥
	 */
	public <T extends Key> T getKey();

	/**
	 * 获取算法名称
	 *
	 * @return 算法名称 如: AES
	 */
	public default String getAlgorithm() {
		return getKey().getAlgorithm();
	}

}
