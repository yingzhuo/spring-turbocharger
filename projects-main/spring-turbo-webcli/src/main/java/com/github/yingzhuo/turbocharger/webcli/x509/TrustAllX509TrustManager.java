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

package com.github.yingzhuo.turbocharger.webcli.x509;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * {@link X509TrustManager} 实现，这种实现不会检查任何证书信息。<br>
 * <em>注意: 使用本类不安全</em>
 *
 * @author 应卓
 * @see #getInstance()
 * @since 3.3.0
 */
public final class TrustAllX509TrustManager implements X509TrustManager {

	/**
	 * 私有构造方法
	 */
	private TrustAllX509TrustManager() {
	}

	/**
	 * 获取单例实例
	 *
	 * @return 实例
	 */
	public static TrustAllX509TrustManager getInstance() {
		return SyncAvoid.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		// noop
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		// noop
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 延迟加载
	 */
	private static final class SyncAvoid {
		private static final TrustAllX509TrustManager INSTANCE = new TrustAllX509TrustManager();
	}

}
