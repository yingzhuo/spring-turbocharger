package com.github.yingzhuo.turbocharger.webcli.cli;

import org.jspecify.annotations.Nullable;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

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
//		factory.setConnectTimeout(connectTimeout);
		factory.setRequestTimeout(requestTimeout);
		return (HttpComponentsClientHttpRequestFactory) factory.getObject();
	}

}
