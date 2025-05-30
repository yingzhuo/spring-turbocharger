/*
 *
 * Copyright 2022-present the original author or authors.
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

import com.github.yingzhuo.turbocharger.keystore.util.KeyStoreUtils;
import com.github.yingzhuo.turbocharger.webcli.cli.ClientCertificate;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
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
	protected Duration connectTimeout;

	@Nullable
	protected Duration requestTimeout;

	@Nullable
	private ClientCertificate clientCertificate;

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

		if (clientCertificate != null) {
			var keyStore = KeyStoreUtils.loadKeyStore(
				clientCertificate.getResource().getInputStream(),
				clientCertificate.getKeyStoreFormat(),
				clientCertificate.getStorePassword()
			);
			builder.loadKeyMaterial(keyStore, clientCertificate.getKeyPassword().toCharArray());
		}

		return builder.build();
	}

}
