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
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Optional;

/**
 * {@link ClientHttpRequestFactory} 的 <a href="https://hc.apache.org/httpcomponents-client-ga/">ApacheHttpComponents官方文档</a> 版本的实现 <br>
 * <em>注意: 使用本类产生的 ClientHttpRequestFactory 默认使用 {@link TrustAllStrategy}。自担风险</em>
 *
 * @author 杨洋
 * @author 应卓
 * @see org.springframework.web.client.RestClient
 * @see org.springframework.web.client.RestTemplate
 * @since 3.3.1
 */
public class Apache5ClientHttpRequestFactoryBean implements FactoryBean<ClientHttpRequestFactory>, InitializingBean {

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

	private @Nullable HttpComponentsClientHttpRequestFactory factory = null;

	/**
	 * 默认构造方法
	 */
	public Apache5ClientHttpRequestFactoryBean() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClientHttpRequestFactory getObject() {
		Assert.notNull(this.factory, "factory is not set");
		return factory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getObjectType() {
		return ClientHttpRequestFactory.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void afterPropertiesSet() {
		var sslContext = SSLContextFactories.createInsecureSSLContext(
			this.clientCertificate,
			this.clientCertificateFormat,
			this.clientCertificatePassword
		);

		// 这里大量使用已过时的代码
		var socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
			.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory())
			.build();

		var httpClient = HttpClientBuilder.create()
			.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
			.setConnectionManagerShared(true)
			.build();

		this.factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		Optional.ofNullable(this.requestTimeout).ifPresent(d -> factory.setConnectionRequestTimeout(d));
		Optional.ofNullable(this.connectTimeout).ifPresent(d -> factory.setConnectTimeout(d));
	}

}
