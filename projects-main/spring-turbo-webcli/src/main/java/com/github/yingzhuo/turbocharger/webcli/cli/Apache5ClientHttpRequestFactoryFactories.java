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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * {@link HttpComponentsClientHttpRequestFactory} 生成工具
 *
 * @author 应卓
 * @see Apache5ClientHttpRequestFactoryBean
 * @since 3.3.1
 */
public final class Apache5ClientHttpRequestFactoryFactories {

	/**
	 * 私有构造方法
	 */
	private Apache5ClientHttpRequestFactoryFactories() {
	}

	/**
	 * 创建 {@link HttpComponentsClientHttpRequestFactory} 默认对象
	 *
	 * @return {@link HttpComponentsClientHttpRequestFactory} 默认对象
	 */
	public static HttpComponentsClientHttpRequestFactory create() {
		return create(null, null, null, null, null);
	}

	/**
	 * 创建 {@link HttpComponentsClientHttpRequestFactory} 对象
	 *
	 * @param connectTimeout 连接超时时间
	 * @param requestTimeout 请求超时时间
	 * @return {@link HttpComponentsClientHttpRequestFactory} 对象
	 */
	public static HttpComponentsClientHttpRequestFactory create(
		@Nullable Duration connectTimeout,
		@Nullable Duration requestTimeout) {
		return create(null, null, null, connectTimeout, requestTimeout);
	}

	/**
	 * 创建 {@link HttpComponentsClientHttpRequestFactory} 对象
	 *
	 * @param clientSideCertificate         SSL客户端证书
	 * @param clientSideCertificateFormat   SSL客户端证书类型
	 * @param clientSideCertificatePassword SSL客户端证书密码
	 * @return {@link HttpComponentsClientHttpRequestFactory} 对象
	 */
	public static HttpComponentsClientHttpRequestFactory create(
		@Nullable Resource clientSideCertificate,
		@Nullable KeyStoreFormat clientSideCertificateFormat,
		@Nullable String clientSideCertificatePassword) {

		return create(clientSideCertificate, clientSideCertificateFormat, clientSideCertificatePassword, null, null);
	}

	/**
	 * 创建 {@link HttpComponentsClientHttpRequestFactory} 对象
	 *
	 * @param clientSideCertificate         SSL客户端证书
	 * @param clientSideCertificateFormat   SSL客户端证书类型
	 * @param clientSideCertificatePassword SSL客户端证书密码
	 * @param connectTimeout                连接超时时间
	 * @param requestTimeout                请求超时时间
	 * @return {@link HttpComponentsClientHttpRequestFactory} 对象
	 */
	public static HttpComponentsClientHttpRequestFactory create(
		@Nullable Resource clientSideCertificate,
		@Nullable KeyStoreFormat clientSideCertificateFormat,
		@Nullable String clientSideCertificatePassword,
		@Nullable Duration connectTimeout,
		@Nullable Duration requestTimeout) {

		try {
			var factoryBean = new Apache5ClientHttpRequestFactoryBean();
			factoryBean.setClientSideCertificate(clientSideCertificate);
			factoryBean.setClientSideCertificateFormat(clientSideCertificateFormat);
			factoryBean.setClientSideCertificatePassword(clientSideCertificatePassword);
			factoryBean.setConnectTimeout(connectTimeout);
			factoryBean.setRequestTimeout(requestTimeout);
			factoryBean.afterPropertiesSet();
			var beanObject = factoryBean.getObject();
			if (beanObject == null) {
				throw new IllegalArgumentException("Cannot create HttpComponentsClientHttpRequestFactory instance");
			}
			return CastUtils.castNonNull(beanObject);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
