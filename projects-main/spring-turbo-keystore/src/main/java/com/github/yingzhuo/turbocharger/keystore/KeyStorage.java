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
package com.github.yingzhuo.turbocharger.keystore;

import com.github.yingzhuo.turbocharger.keystore.util.KeyStoreUtils;
import org.springframework.core.style.ToStringCreator;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.KeyStore;
import java.util.List;

import static com.github.yingzhuo.turbocharger.core.ResourceUtils.loadResourceAsInputStream;
import static com.github.yingzhuo.turbocharger.keystore.util.KeyStoreUtils.loadKeyStore;

/**
 * 对 {@link KeyStore} 的封装
 *
 * @author 应卓
 * @see KeyStore
 * @see KeyStoreUtils
 * @see KeyStorageFactoryBean
 * @since 3.5.0
 */
public final class KeyStorage implements Serializable {

	/**
	 * 从PKCS#12格式文件中加载
	 *
	 * @param location 资源位置
	 * @param keyStoreFormat 库文件类型
	 * @param storepass 库密码
	 * @return 实例
	 */
	public static KeyStorage load(String location, KeyStoreFormat keyStoreFormat, String storepass) {
		var ks = loadKeyStore(
			loadResourceAsInputStream(location),
			keyStoreFormat,
			storepass
		);
		return new KeyStorage(ks, location);
	}

	private final KeyStore keyStore;
	private final String location;

	/**
	 * 私有构造方法
	 *
	 * @param keyStore 底层秘钥库
	 */
	private KeyStorage(KeyStore keyStore, String location) {
		this.keyStore = keyStore;
		this.location = location;
	}

	/**
	 * 获取所有条目名称
	 *
	 * @return 所有条目名称
	 */
	public List<String> getAliases() {
		return KeyStoreUtils.getAliases(keyStore);
	}

	public KeyBundle getKeyBundle(String alias, String keypass) {
		return new KeyBundleImpl(
			KeyStoreUtils.getKeyPair(keyStore, alias, keypass),
			KeyStoreUtils.getCertificate(keyStore, alias)
		);
	}

	public SecretKey getSecretKey(String alias, String keypass) {
		return KeyStoreUtils.getSecretKey(keyStore, alias, keypass);
	}

	public KeyStore toKeyStore() {
		return keyStore;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		var creator = new ToStringCreator(this);
		creator.append("keyStore", keyStore);
		creator.append("location", location);
		return creator.toString();
	}
}
