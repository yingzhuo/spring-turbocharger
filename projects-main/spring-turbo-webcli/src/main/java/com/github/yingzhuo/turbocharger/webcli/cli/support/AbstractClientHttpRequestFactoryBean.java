package com.github.yingzhuo.turbocharger.webcli.cli.support;

import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.client.ClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;
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
	@Deprecated(forRemoval = true)
	protected Duration connectTimeout;

	@Nullable
	protected Duration requestTimeout;

	@Nullable
	private KeyStore loadedKeyStore;

	@Nullable
	private String keyPassword;

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

	@SneakyThrows
	protected final SSLContext getSslContext() {
		var builder = SSLContextBuilder.create()
			.loadTrustMaterial(TrustAllStrategy.INSTANCE);

		if (loadedKeyStore != null && keyPassword != null) {
			builder.loadKeyMaterial(loadedKeyStore, keyPassword.toCharArray());
		}

		return builder.build();
	}

}
