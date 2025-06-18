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
package com.github.yingzhuo.turbocharger.util.io.resource;

import com.github.yingzhuo.turbocharger.util.keystore.KeyStoreType;
import com.github.yingzhuo.turbocharger.util.keystore.KeyStoreUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.List;

/**
 * 基于{@link KeyStore}的资源
 *
 * @author 应卓
 * @see KeyStore
 * @see KeyStoreType
 * @see KeyStoreUtils
 * @see JKSResource
 * @see PKCS12Resource
 * @since 3.5.0
 */
public class KeyStoreResource extends AbstractNullStreamResource {

	private final KeyStoreType type;
	private final KeyStore store;
	private final String storepass;

	public KeyStoreResource(InputStream in, KeyStoreType type, String storepass) {
		Assert.notNull(in, "inputStream must not be null");
		Assert.notNull(type, "type must not be null");
		Assert.notNull(storepass, "storepass must not be null");

		this.type = type;
		this.storepass = storepass;
		this.store = KeyStoreUtils.loadKeyStore(in, type, storepass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getDescription() {
		return "KeyStore Resource [%s] [%d]".formatted(type.getValue().toUpperCase(), hashCode());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return getDescription();
	}

	public final String getStorepass() {
		return this.storepass;
	}

	public final KeyStoreType getType() {
		return this.type;
	}

	public final KeyStore getKeyStore() {
		return this.store;
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 获取证书链
	 *
	 * @param alias 条目名称
	 * @return 证书链
	 * @see #getCertificate(String)
	 */
	public final List<Certificate> getCertificateChain(String alias) {
		return KeyStoreUtils.getCertificateChain(store, alias);
	}

	/**
	 * 从密钥库中获取证书，公钥保存在证书之中
	 *
	 * @param alias 条目名称
	 * @param <T>   证书类型的泛型
	 * @return 证书
	 * @see #getCertificateChain(String)
	 */
	public final <T extends Certificate> T getCertificate(String alias) {
		return KeyStoreUtils.getCertificate(store, alias);
	}

	/**
	 * 从密钥库中获取公钥
	 *
	 * @param alias 条目名称
	 * @param <T>   私钥类型的泛型
	 * @return 公钥
	 * @see #getPrivateKey(String)
	 * @see #getPrivateKey(String, String)
	 */
	public final <T extends PublicKey> T getPublicKey(String alias) {
		return KeyStoreUtils.getPublicKey(store, alias);
	}

	/**
	 * 从秘钥库中获取私钥
	 *
	 * @param alias 条目名称
	 * @param <T>   私钥类型的泛型
	 * @return 私钥
	 */
	public final <T extends PrivateKey> T getPrivateKey(String alias) {
		return getPrivateKey(alias, null);
	}

	/**
	 * 从秘钥库中获取私钥
	 *
	 * @param alias   条目名称
	 * @param keypass 私钥的密码
	 * @param <T>     私钥类型的泛型
	 * @return 私钥
	 */
	public final <T extends PrivateKey> T getPrivateKey(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getPrivateKey(store, alias, keypass == null ? storepass : keypass);
	}

	/**
	 * 获取秘钥
	 *
	 * @param alias 条目名称
	 * @param <T>   秘钥类型泛型
	 * @return 秘钥
	 */
	public final <T extends Key> T getKey(String alias) {
		return getKey(alias, null);
	}

	/**
	 * 获取秘钥
	 *
	 * @param alias   条目名称
	 * @param keypass 秘钥的密码
	 * @param <T>     秘钥类型泛型
	 * @return 秘钥
	 */
	public final <T extends Key> T getKey(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getKey(store, alias, keypass == null ? storepass : keypass);
	}

	/**
	 * 获取秘钥
	 *
	 * @param alias 条目名称
	 * @param <T>   秘钥类型的泛型
	 * @return 秘钥
	 */
	public final <T extends SecretKey> T getSecretKey(String alias) {
		return getSecretKey(alias, null);
	}

	/**
	 * 获取秘钥
	 *
	 * @param alias   条目名称
	 * @param keypass 条目秘钥
	 * @param <T>     秘钥类型的泛型
	 * @return 秘钥
	 */
	public final <T extends SecretKey> T getSecretKey(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getSecretKey(store, alias, keypass == null ? storepass : keypass);
	}

	/**
	 * 从密钥库中获取密钥对
	 *
	 * @param alias   条目名称
	 * @param keypass 私钥类型的泛型
	 * @return 密钥对
	 */
	public final KeyPair getKeyPair(String alias, @Nullable String keypass) {
		return KeyStoreUtils.getKeyPair(store, alias, keypass == null ? storepass : keypass);
	}

	/**
	 * 获取所有条目名称
	 *
	 * @return 所有条目名称
	 */
	public final List<String> getAliases() {
		return KeyStoreUtils.getAliases(store);
	}

	/**
	 * 测试秘钥库是否包含条目
	 *
	 * @param alias 条目名称
	 * @return 结果
	 */
	public final boolean containsAlias(String alias) {
		return KeyStoreUtils.containsAlias(store, alias);
	}

}
