/*
 *
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
 *
 */
package com.github.yingzhuo.turbocharger.webcli.cli;

import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * {@link JdkClientHttpRequestFactory} 生成工具
 *
 * @author 应卓
 * @see JdkClientHttpRequestFactoryBean
 * @since 3.4.3
 */
public final class JdkClientHttpRequestFactoryFactories {

	/**
	 * 私有构造方法
	 */
	private JdkClientHttpRequestFactoryFactories() {
		super();
	}

	public static JdkClientHttpRequestFactory create() {
		return create(null, null, null, null);
	}

	public static JdkClientHttpRequestFactory create(
		@Nullable ClientCertificate certificate,
		@Nullable Duration connectTimeout,
		@Nullable Duration readTimeout,
		@Nullable Executor executor
	) {
		var factoryBean = new JdkClientHttpRequestFactoryBean();
		factoryBean.setClientCertificate(certificate);
		factoryBean.setConnectTimeout(connectTimeout);
		factoryBean.setRequestTimeout(readTimeout);
		factoryBean.setExecutor(executor);
		return (JdkClientHttpRequestFactory) factoryBean.getObject();
	}

}
