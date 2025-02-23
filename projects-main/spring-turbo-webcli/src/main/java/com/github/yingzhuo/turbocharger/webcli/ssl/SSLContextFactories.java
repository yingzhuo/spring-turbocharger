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
package com.github.yingzhuo.turbocharger.webcli.ssl;

import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreHelper;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import javax.net.ssl.SSLContext;

/**
 * {@link javax.net.ssl.SSLContext} 生成工具
 *
 * @author 应卓
 * @since 3.4.3
 */
public final class SSLContextFactories {

	/**
	 * 私有构造方法
	 */
	private SSLContextFactories() {
		super();
	}

	/**
	 * 生成SSL上下文
	 *
	 * @return SSL上下文
	 */
	public static SSLContext createInsecureSSLContext() {
		try {
			return SSLContextBuilder.create()
				.loadTrustMaterial(TrustAllStrategy.INSTANCE)
				.build();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 生成携带客户端证书的SSL上下文
	 *
	 * @param clientCertificate         客户端证书资源
	 * @param clientCertificatePassword 秘钥
	 * @return SSL上下文
	 */
	public static SSLContext createInsecureSSLContext(@Nullable Resource clientCertificate, @Nullable String clientCertificatePassword) {
		return createInsecureSSLContext(clientCertificate, KeyStoreFormat.PKCS12, clientCertificatePassword);
	}

	/**
	 * 生成携带客户端证书的SSL上下文
	 *
	 * @param clientCertificate         客户端证书资源
	 * @param clientCertificateFormat   客户端证书格式
	 * @param clientCertificatePassword 秘钥
	 * @return SSL上下文
	 */
	public static SSLContext createInsecureSSLContext(
		@Nullable Resource clientCertificate,
		@Nullable KeyStoreFormat clientCertificateFormat,
		@Nullable String clientCertificatePassword) {

		try {
			var builder = SSLContextBuilder.create()
				.loadTrustMaterial(TrustAllStrategy.INSTANCE);

			if (clientCertificate != null && clientCertificatePassword != null) {
				var keyStore = KeyStoreHelper.loadKeyStore(
					clientCertificate.getInputStream(),
					clientCertificateFormat,
					clientCertificatePassword
				);
				builder.loadKeyMaterial(keyStore, clientCertificatePassword.toCharArray());
			}

			return builder.build();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
