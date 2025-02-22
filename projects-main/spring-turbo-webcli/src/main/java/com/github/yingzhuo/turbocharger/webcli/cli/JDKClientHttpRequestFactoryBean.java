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

import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.webcli.ssl.SSLContextFactories;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * @author 应卓
 * @since 3.4.3
 */
public class JDKClientHttpRequestFactoryBean implements FactoryBean<ClientHttpRequestFactory>, InitializingBean {

	@Setter
	@Nullable
	private Resource clientCertificate;

	@Setter
	@Nullable
	private KeyStoreFormat clientCertificateFormat = KeyStoreFormat.PKCS12;

	@Setter
	@Nullable
	private String clientCertificatePassword;

	@Setter
	@Nullable
	private Duration connectTimeout;

	@Setter
	@Nullable
	private Duration requestTimeout;

	@Setter
	@Nullable
	private Executor executor;

	@Nullable
	private JdkClientHttpRequestFactory factory = null;

	@Nullable
	@Override
	public ClientHttpRequestFactory getObject() {
		Assert.notNull(this.factory, "factory is not set");
		return factory;
	}

	@Nullable
	@Override
	public Class<?> getObjectType() {
		return ClientHttpRequestFactory.class;
	}

	@Override
	public void afterPropertiesSet() {
		var sslContext = SSLContextFactories.createInsecureSSLContext(
			this.clientCertificate,
			this.clientCertificateFormat,
			this.clientCertificatePassword
		);

		var httpClientBuilder = HttpClient.newBuilder()
			.sslContext(sslContext);

		if (this.requestTimeout != null) {
			httpClientBuilder.connectTimeout(this.requestTimeout);
		}

		if (this.connectTimeout != null) {
			httpClientBuilder.connectTimeout(this.connectTimeout);
		}

		if (this.executor != null) {
			httpClientBuilder.executor(this.executor);
		}

		this.factory = new JdkClientHttpRequestFactory(httpClientBuilder.build());
	}

}
