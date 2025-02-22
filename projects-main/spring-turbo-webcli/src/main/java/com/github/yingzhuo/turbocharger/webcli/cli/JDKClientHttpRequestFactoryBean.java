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

import com.github.yingzhuo.turbocharger.webcli.cli.support.AbstractClientHttpRequestFactoryBean;
import lombok.Setter;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.net.http.HttpClient;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * @author 应卓
 * @since 3.4.3
 */
@Setter
public class JDKClientHttpRequestFactoryBean extends AbstractClientHttpRequestFactoryBean {

	@Nullable
	private Executor executor;

	@Nullable
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

		return new JdkClientHttpRequestFactory(httpClientBuilder.build());
	}

}
