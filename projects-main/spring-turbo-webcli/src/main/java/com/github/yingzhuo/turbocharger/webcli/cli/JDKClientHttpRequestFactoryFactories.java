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

import com.github.yingzhuo.turbocharger.util.CastUtils;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import org.springframework.core.io.Resource;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * 创建{@link JdkClientHttpRequestFactory} 通用工具
 *
 * @author 应卓
 * @see JDKClientHttpRequestFactoryBean
 * @since 3.4.3
 */
public final class JDKClientHttpRequestFactoryFactories {

	/**
	 * 私有构造方法
	 */
	private JDKClientHttpRequestFactoryFactories() {
	}

	/**
	 * 创建 {@link JdkClientHttpRequestFactory} 对象
	 *
	 * @return {@link JdkClientHttpRequestFactory} 对象
	 */
	public static JdkClientHttpRequestFactory create() {
		return create(null, null, null, null, null, null);
	}

	/**
	 * 创建 {@link JdkClientHttpRequestFactory} 对象
	 *
	 * @param clientCertificate         SSL客户端证书
	 * @param clientCertificateFormat   SSL客户端证书类型
	 * @param clientCertificatePassword SSL客户端证书密码
	 * @return {@link JdkClientHttpRequestFactory} 对象
	 */
	public static JdkClientHttpRequestFactory create(
		@Nullable Resource clientCertificate,
		@Nullable KeyStoreFormat clientCertificateFormat,
		@Nullable String clientCertificatePassword) {
		return create(clientCertificate, clientCertificateFormat, clientCertificatePassword, null, null, null);
	}

	/**
	 * 创建 {@link JdkClientHttpRequestFactory} 对象
	 *
	 * @param clientCertificate         SSL客户端证书
	 * @param clientCertificateFormat   SSL客户端证书类型
	 * @param clientCertificatePassword SSL客户端证书密码
	 * @param connectTimeout            连接超时时间
	 * @param requestTimeout            请求超时时间
	 * @return {@link JdkClientHttpRequestFactory} 对象
	 */
	public static JdkClientHttpRequestFactory create(
		@Nullable Resource clientCertificate,
		@Nullable KeyStoreFormat clientCertificateFormat,
		@Nullable String clientCertificatePassword,
		@Nullable Duration connectTimeout,
		@Nullable Duration requestTimeout) {
		return create(clientCertificate, clientCertificateFormat, clientCertificatePassword, connectTimeout, requestTimeout, null);
	}

	/**
	 * 创建 {@link JdkClientHttpRequestFactory} 对象
	 *
	 * @param clientCertificate         SSL客户端证书
	 * @param clientCertificateFormat   SSL客户端证书类型
	 * @param clientCertificatePassword SSL客户端证书密码
	 * @param connectTimeout            连接超时时间
	 * @param requestTimeout            请求超时时间
	 * @param executor                  线程池
	 * @return {@link JdkClientHttpRequestFactory} 对象
	 */
	public static JdkClientHttpRequestFactory create(
		@Nullable Resource clientCertificate,
		@Nullable KeyStoreFormat clientCertificateFormat,
		@Nullable String clientCertificatePassword,
		@Nullable Duration connectTimeout,
		@Nullable Duration requestTimeout,
		@Nullable Executor executor
	) {
		try {
			var factoryBean = new JDKClientHttpRequestFactoryBean();
			factoryBean.setClientCertificate(clientCertificate);
			factoryBean.setClientCertificateFormat(clientCertificateFormat);
			factoryBean.setClientCertificatePassword(clientCertificatePassword);
			factoryBean.setConnectTimeout(connectTimeout);
			factoryBean.setRequestTimeout(requestTimeout);
			factoryBean.setExecutor(executor);
			factoryBean.afterPropertiesSet();
			var beanObject = factoryBean.getObject();
			if (beanObject == null) {
				throw new IllegalArgumentException("Cannot create JDKClientHttpRequestFactoryBean instance");
			}
			return CastUtils.castNonNull(beanObject);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
