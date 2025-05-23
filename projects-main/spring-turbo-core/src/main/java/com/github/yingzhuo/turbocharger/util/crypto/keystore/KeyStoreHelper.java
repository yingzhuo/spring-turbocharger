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
package com.github.yingzhuo.turbocharger.util.crypto.keystore;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * {@link KeyStore} 相关工具类
 *
 * @author 应卓
 * @see KeyStoreFormat
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
public final class KeyStoreHelper {

	/**
	 * 私有构造方法
	 */
	private KeyStoreHelper() {
		super();
	}

	/**
	 * 加载密钥库
	 *
	 * @param inputStream 输入流
	 * @param storepass   秘钥库的口令
	 * @return 密钥库
	 * @throws UncheckedIOException     IO错误
	 * @throws IllegalArgumentException 其他错误
	 */
	public static KeyStore loadKeyStore(InputStream inputStream, String storepass) {
		return loadKeyStore(inputStream, KeyStoreFormat.PKCS12, storepass);
	}

	/**
	 * 加载密钥库
	 *
	 * @param inputStream    输入流
	 * @param keyStoreFormat 密钥库格式
	 * @param storepass      秘钥库的口令
	 * @return 密钥库
	 * @throws UncheckedIOException     IO错误
	 * @throws IllegalArgumentException 其他错误
	 */
	public static KeyStore loadKeyStore(InputStream inputStream, @Nullable KeyStoreFormat keyStoreFormat, String storepass) {
		Assert.notNull(inputStream, "inputStream is required");
		Assert.notNull(storepass, "storepass is required");

		keyStoreFormat = Objects.requireNonNullElse(keyStoreFormat, KeyStoreFormat.PKCS12);

		try {
			var keyStore = KeyStore.getInstance(keyStoreFormat.getValue());
			keyStore.load(inputStream, storepass.toCharArray());
			return keyStore;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 获取秘钥
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @param keypass  秘钥的密码
	 * @param <T>      秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T getKey(KeyStore keyStore, String alias, String keypass) {
		Assert.notNull(keyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");
		Assert.notNull(keypass, "privateKeyPass is required");

		try {
			return (T) keyStore.getKey(alias, keypass.toCharArray());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 从秘钥库中获取私钥
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @param keypass  私钥的密码
	 * @param <T>      私钥类型的泛型
	 * @return 私钥
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends PrivateKey> T getPrivateKey(KeyStore keyStore, String alias, String keypass) {
		return getKey(keyStore, alias, keypass);
	}

	/**
	 * 从密钥库中获取公钥
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @param <T>      私钥类型的泛型
	 * @return 公钥
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends PublicKey> T getPublicKey(KeyStore keyStore, String alias) {
		var cert = getCertificate(keyStore, alias);
		return (T) cert.getPublicKey();
	}

	/**
	 * 从密钥库中获取证书，公钥保存在证书之中
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @param <T>      证书类型的泛型
	 * @return 证书
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends Certificate> T getCertificate(KeyStore keyStore, String alias) {
		Assert.notNull(keyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");

		try {
			return (T) keyStore.getCertificate(alias);
		} catch (KeyStoreException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 从密钥库中获取密钥对
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @param keypass  私钥类型的泛型
	 * @return 密钥对
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static KeyPair getKeyPair(KeyStore keyStore, String alias, String keypass) {
		return new KeyPair(
			getPublicKey(keyStore, alias),
			getPrivateKey(keyStore, alias, keypass)
		);
	}

	/**
	 * 获取签名算法名称
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @return 签名算法名称
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static String getSigAlgName(KeyStore keyStore, String alias) {
		var cert = getCertificate(keyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgName();
		}
		throw new IllegalArgumentException("cannot get SigAlg");
	}

	/**
	 * 获取签名算法OID
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @return 签名算法OID
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static String getSigAlgOID(KeyStore keyStore, String alias) {
		var cert = getCertificate(keyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgOID();
		}
		throw new IllegalArgumentException("cannot get SigAlgOID");
	}

	/**
	 * 获取所有条目名称
	 *
	 * @param keyStore 已加载的密钥库
	 * @return 所有条目名称
	 */
	public static List<String> getAliases(KeyStore keyStore) {
		try {
			return Collections.list(keyStore.aliases());
		} catch (KeyStoreException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 测试秘钥库是否包含条目
	 *
	 * @param keyStore 已加载的密钥库
	 * @param alias    条目名称
	 * @return 结果
	 * @see #loadKeyStore(InputStream, String) 加载密钥库
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static boolean containsAlias(KeyStore keyStore, String alias) {
		try {
			return keyStore.containsAlias(alias);
		} catch (KeyStoreException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
