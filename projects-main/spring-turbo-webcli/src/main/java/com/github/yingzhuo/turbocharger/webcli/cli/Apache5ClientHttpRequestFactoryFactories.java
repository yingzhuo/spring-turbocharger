/*
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
 */
package com.github.yingzhuo.turbocharger.webcli.cli;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.security.KeyStore;
import java.time.Duration;

/**
 * {@link HttpComponentsClientHttpRequestFactory} 生成工具
 *
 * @author 应卓
 * @see Apache5ClientHttpRequestFactoryBean
 * @since 3.4.3
 */
public final class Apache5ClientHttpRequestFactoryFactories {

	/**
	 * 私有构造方法
	 */
	private Apache5ClientHttpRequestFactoryFactories() {
	}

	public static HttpComponentsClientHttpRequestFactory create() {
		return create(null, null, null, null);
	}

	public static HttpComponentsClientHttpRequestFactory create(
		@Nullable KeyStore loadedKeyStore,
		@Nullable String keyStorePassword,
		@Nullable Duration connectTimeout,
		@Nullable Duration requestTimeout
	) {
		var factory = new Apache5ClientHttpRequestFactoryBean();
		factory.setLoadedKeyStore(loadedKeyStore);
		factory.setKeyPassword(keyStorePassword);
		factory.setConnectTimeout(connectTimeout);
		factory.setRequestTimeout(requestTimeout);
		return (HttpComponentsClientHttpRequestFactory) factory.getObject();
	}

}
