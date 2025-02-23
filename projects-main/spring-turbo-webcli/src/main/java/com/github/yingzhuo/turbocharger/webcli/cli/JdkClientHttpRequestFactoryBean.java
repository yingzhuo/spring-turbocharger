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
package com.github.yingzhuo.turbocharger.webcli.cli;

import com.github.yingzhuo.turbocharger.webcli.cli.support.AbstractClientHttpRequestFactoryBean;
import lombok.Setter;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.net.http.HttpClient;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * {@link JdkClientHttpRequestFactory} 工厂。<br>
 * <em>注意: 使用本类产生的 ClientHttpRequestFactory 默认使用 {@link TrustAllStrategy}。自担风险</em>
 *
 * @author 应卓
 * @see org.springframework.web.client.RestClient
 * @see org.springframework.web.client.RestTemplate
 * @since 3.4.3
 */
@Deprecated
@Setter
public class JdkClientHttpRequestFactoryBean extends AbstractClientHttpRequestFactoryBean {

	@Nullable
	private Executor executor;

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link ClientHttpRequestFactory} 实例
	 */
	@Override
	public ClientHttpRequestFactory getObject() {
		var sslContext = super.getSslContext();

		var httpClientBuilder = HttpClient.newBuilder()
			.sslContext(sslContext);

		Optional.ofNullable(connectTimeout).ifPresent(httpClientBuilder::connectTimeout);
		Optional.ofNullable(executor).ifPresent(httpClientBuilder::executor);

		if (this.executor != null) {
			httpClientBuilder.executor(this.executor);
		}

		var bean = new JdkClientHttpRequestFactory(httpClientBuilder.build());
		if (requestTimeout != null) {
			bean.setReadTimeout(requestTimeout);
		}
		return bean;
	}

}
