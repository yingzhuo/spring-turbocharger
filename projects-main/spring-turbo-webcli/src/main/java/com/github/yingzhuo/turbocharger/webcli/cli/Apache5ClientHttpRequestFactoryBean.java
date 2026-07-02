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

public class Apache5ClientHttpRequestFactoryBean extends AbstractClientHttpRequestFactoryBean {

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
//		Optional.ofNullable(super.connectTimeout)
//			.ifPresent(bean::setConnectTimeout);

		return bean;
	}

}
