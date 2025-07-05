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
package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.KeyStoreUtils;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.security.cert.X509Certificate;

/**
 * @author 应卓
 * @since 3.5.3
 */
public final class KeyBundleFactories {

	/**
	 * 私有构造方法
	 */
	private KeyBundleFactories() {
		super();
	}

	/**
	 * 从资源中加载
	 *
	 * @param location 资源位置
	 * @param keypass  私钥密码
	 * @return {@link KeyBundle} 实例
	 */
	public static KeyBundle fromPem(String location, @Nullable String keypass) {
		var pc = PemContent.of(ResourceUtils.readResourceAsString(location));
		return new KeyBundleImpl(pc.getCertificates().get(0), pc.getPrivateKey(keypass));
	}

	/**
	 * 从PKCS#12或JKS中加载
	 *
	 * @param location  资源位置
	 * @param type      资源类型
	 * @param storepass 库密码
	 * @param alias     别名
	 * @param keypass   私钥密码
	 * @return {@link KeyBundle} 实例
	 */
	public static KeyBundle fromStore(String location, @Nullable KeyStoreType type, String storepass, String alias, @Nullable String keypass) {
		var input = ResourceUtils.loadResourceAsInputStream(location);
		var ks = KeyStoreUtils.loadKeyStore(input, type != null ? type : KeyStoreType.PKCS12, storepass);

		if (keypass == null) {
			keypass = storepass;
		}

		var cert = (X509Certificate) KeyStoreUtils.getCertificate(ks, alias);
		var privateKey = KeyStoreUtils.getPrivateKey(ks, alias, keypass);
		return new KeyBundleImpl(cert, privateKey, alias);
	}

}
