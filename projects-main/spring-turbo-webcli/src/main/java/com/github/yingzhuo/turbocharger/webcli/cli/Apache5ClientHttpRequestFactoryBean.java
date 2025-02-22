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
import com.github.yingzhuo.turbocharger.webcli.cli.support.InsecureTrustStrategy;
import com.github.yingzhuo.turbocharger.webcli.x509.TrustAllX509TrustManager;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Optional;

/**
 * {@link ClientHttpRequestFactory} 的 <a href="https://hc.apache.org/httpcomponents-client-ga/">ApacheHttpComponents官方文档</a> 版本的实现 <br>
 * <em>注意: 使用本类产生的 ClientHttpRequestFactory 默认使用 {@link TrustAllX509TrustManager}。自担风险</em>
 *
 * @author 杨洋
 * @author 应卓
 * @see org.springframework.web.client.RestClient
 * @see org.springframework.web.client.RestTemplate
 * @since 3.3.1
 */
public class Apache5ClientHttpRequestFactoryBean implements FactoryBean<ClientHttpRequestFactory>, InitializingBean {

	private @Nullable Resource clientSideCertificate;
	private @Nullable KeyStoreFormat clientSideCertificateFormat = KeyStoreFormat.PKCS12;
	private @Nullable String clientSideCertificatePassword;
	private @Nullable Duration connectTimeout;
	private @Nullable Duration requestTimeout;

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
	public boolean isSingleton() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void afterPropertiesSet() throws Exception {
		var sslContext = createSSLContext(
			this.clientSideCertificate,
			this.clientSideCertificateFormat,
			this.clientSideCertificatePassword
		);

		// 这里大量使用已过时的代码
		var socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
			.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory())
			.build();

//		var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
//			.setTlsSocketStrategy(new DefaultClientTlsStrategy(sslContext, NoopHostnameVerifier.INSTANCE))
//			.build();

		var httpClient = HttpClientBuilder.create()
			.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
			.setConnectionManagerShared(true)
			.build();

		this.factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		Optional.ofNullable(this.requestTimeout).ifPresent(d -> factory.setConnectionRequestTimeout(d));
		Optional.ofNullable(this.connectTimeout).ifPresent(d -> factory.setConnectTimeout(d));
	}

	private SSLContext createSSLContext(
		@Nullable Resource clientSideCertificate,
		@Nullable KeyStoreFormat clientSideCertificateFormat,
		@Nullable String clientSideCertificatePassword) throws Exception {

		KeyStore keyStore = null;

		if (clientSideCertificate != null && clientSideCertificatePassword != null) {
			var ksf = Optional.ofNullable(clientSideCertificateFormat)
				.map(KeyStoreFormat::getValue)
				.orElseGet(KeyStore::getDefaultType);

			keyStore = KeyStore.getInstance(ksf);
			keyStore.load(clientSideCertificate.getInputStream(), clientSideCertificatePassword.toCharArray());
		}

		var contextBuilder =
			SSLContextBuilder.create()
				.loadTrustMaterial(InsecureTrustStrategy.getInstance());

		if (keyStore != null) {
			contextBuilder.loadKeyMaterial(keyStore, clientSideCertificatePassword.toCharArray());
		}

		return contextBuilder.build();
	}

	public void setClientSideCertificate(@Nullable Resource clientSideCertificate) {
		this.clientSideCertificate = clientSideCertificate;
	}

	public void setClientSideCertificateFormat(@Nullable KeyStoreFormat clientSideCertificateFormat) {
		this.clientSideCertificateFormat = clientSideCertificateFormat;
	}

	public void setClientSideCertificatePassword(@Nullable String clientSideCertificatePassword) {
		this.clientSideCertificatePassword = clientSideCertificatePassword;
	}

	public void setRequestTimeout(@Nullable Duration requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public void setConnectTimeout(@Nullable Duration connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
