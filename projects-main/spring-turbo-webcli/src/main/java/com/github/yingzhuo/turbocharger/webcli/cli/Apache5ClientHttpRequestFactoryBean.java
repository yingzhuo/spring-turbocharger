/*
 * Copyright 2022-2026 the original author or authors.
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

import com.github.yingzhuo.turbocharger.webcli.cli.support.AbstractClientHttpRequestFactoryBean;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

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
public class Apache5ClientHttpRequestFactoryBean extends AbstractClientHttpRequestFactoryBean {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("deprecation")
	public ClientHttpRequestFactory getObject() {
		var sslContext = super.getSslContext();

		// 这里大量使用已过时的代码
		var socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
			.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory())
			.build();

		var httpClient = HttpClientBuilder.create()
			.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
			.setConnectionManagerShared(true)
			.build();

		var bean = new HttpComponentsClientHttpRequestFactory(httpClient);

		Optional.ofNullable(super.requestTimeout)
			.ifPresent(bean::setConnectionRequestTimeout);
		Optional.ofNullable(super.connectTimeout)
			.ifPresent(bean::setConnectTimeout);

		return bean;
	}

}
