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
package com.github.yingzhuo.turbocharger.webcli.cli.support;

import com.github.yingzhuo.turbocharger.webcli.cli.ClientCertificate;
import com.github.yingzhuo.turbocharger.webcli.ssl.SSLContextFactories;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import javax.net.ssl.SSLContext;
import java.time.Duration;

/**
 * 内部工具
 *
 * @author 应卓
 * @since 3.4.3
 */
@Setter
public abstract class AbstractClientHttpRequestFactoryBean implements FactoryBean<ClientHttpRequestFactory> {

	@Nullable
	private ClientCertificate clientCertificate;

	@Nullable
	protected Duration connectTimeout;

	@Nullable
	protected Duration requestTimeout;

	/**
	 * 构造方法
	 */
	protected AbstractClientHttpRequestFactoryBean() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<?> getObjectType() {
		return ClientHttpRequestFactory.class;
	}

	protected final SSLContext getSslContext() {
		return clientCertificate == null ?
			SSLContextFactories.createInsecureSSLContext() :
			SSLContextFactories.createInsecureSSLContext(
				clientCertificate.getResource(),
				clientCertificate.getKeyStoreFormat(),
				clientCertificate.getPassword()
			);
	}

}
