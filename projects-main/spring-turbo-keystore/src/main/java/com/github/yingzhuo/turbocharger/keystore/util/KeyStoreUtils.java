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
package com.github.yingzhuo.turbocharger.keystore.util;

import com.github.yingzhuo.turbocharger.keystore.KeyStoreFormat;
import com.github.yingzhuo.turbocharger.util.io.IOExceptionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.yingzhuo.turbocharger.keystore.KeyStoreFormat.PKCS12;
import static java.util.Objects.requireNonNullElse;

/**
 * {@link KeyStore} 相关工具类
 *
 * @author 应卓
 * @see KeyStoreFormat
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
public final class KeyStoreUtils {

	private static final Lock LOCK = new ReentrantLock();

	/**
	 * 私有构造方法
	 */
	private KeyStoreUtils() {
		super();
	}

	/**
	 * 加载密钥库
	 *
	 * @param inputStream 输入流
	 * @param format      密钥库格式
	 * @param storepass   秘钥库的口令
	 * @return 密钥库
	 * @throws UncheckedIOException     IO错误
	 * @throws IllegalArgumentException 其他错误
	 */
	public static KeyStore loadKeyStore(InputStream inputStream, @Nullable KeyStoreFormat format, String storepass) {
		Assert.notNull(inputStream, "inputStream is required");
		Assert.notNull(storepass, "storepass is required");

		format = requireNonNullElse(format, PKCS12);

		LOCK.lock();

		try {
			var keyStore = KeyStore.getInstance(format.getValue());
			keyStore.load(inputStream, storepass.toCharArray());
			return keyStore;
		} catch (IOException e) {
			throw IOExceptionUtils.toUnchecked(e);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			LOCK.unlock();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 获取秘钥
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param keypass        秘钥的密码
	 * @param <T>            秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T getKey(KeyStore loadedKeyStore, String alias, String keypass) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");
		Assert.notNull(keypass, "keypass is required");

		T key = null;
		try {
			key = (T) loadedKeyStore.getKey(alias, keypass.toCharArray());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		if (key == null) {
			throw new IllegalArgumentException("cannot find key with alias: " + alias);
		}
		return key;
	}

	/**
	 * 从秘钥库中获取私钥
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param keypass        私钥的密码
	 * @param <T>            私钥类型的泛型
	 * @return 私钥
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends PrivateKey> T getPrivateKey(KeyStore loadedKeyStore, String alias, String keypass) {
		return getKey(loadedKeyStore, alias, keypass);
	}

	/**
	 * 从密钥库中获取公钥
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param <T>            私钥类型的泛型
	 * @return 公钥
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends PublicKey> T getPublicKey(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		return (T) cert.getPublicKey();
	}

	/**
	 * 从密钥库中获取证书，公钥保存在证书之中
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param <T>            证书类型的泛型
	 * @return 证书
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static <T extends Certificate> T getCertificate(KeyStore loadedKeyStore, String alias) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");

		T certificate = null;
		try {
			certificate = (T) loadedKeyStore.getCertificate(alias);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		if (certificate == null) {
			throw new IllegalArgumentException("cannot find certificate with alias: " + alias);
		}
		return certificate;
	}

	/**
	 * 从密钥库中获取密钥对
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param keypass        私钥类型的泛型
	 * @return 密钥对
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static KeyPair getKeyPair(KeyStore loadedKeyStore, String alias, String keypass) {
		return new KeyPair(getPublicKey(loadedKeyStore, alias), getPrivateKey(loadedKeyStore, alias, keypass));
	}

	/**
	 * 获取签名算法名称
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @return 签名算法名称
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static String getSigAlgName(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgName();
		}
		throw new IllegalArgumentException("cannot get SigAlg");
	}

	/**
	 * 获取签名算法OID
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @return 签名算法OID
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static String getSigAlgOID(KeyStore loadedKeyStore, String alias) {
		var cert = getCertificate(loadedKeyStore, alias);
		if (cert instanceof X509Certificate x509Cert) {
			return x509Cert.getSigAlgOID();
		}
		throw new IllegalArgumentException("cannot get SigAlgOID");
	}

	/**
	 * 获取秘钥
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @param password       条目秘钥
	 * @param <T>            秘钥类型的泛型
	 * @return 秘钥
	 */
	public static <T extends SecretKey> T getSecretKey(KeyStore loadedKeyStore, String alias, String password) {
		return getKey(loadedKeyStore, alias, password);
	}

	/**
	 * 获取所有条目名称
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @return 所有条目名称
	 */
	public static List<String> getAliases(KeyStore loadedKeyStore) {
		Assert.notNull(loadedKeyStore, "keyStore is required");

		try {
			return Collections.unmodifiableList(Collections.list(loadedKeyStore.aliases()));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 测试秘钥库是否包含条目
	 *
	 * @param loadedKeyStore 已加载的密钥库
	 * @param alias          条目名称
	 * @return 结果
	 * @see #loadKeyStore(InputStream, KeyStoreFormat, String) 加载密钥库
	 */
	public static boolean containsAlias(KeyStore loadedKeyStore, String alias) {
		Assert.notNull(loadedKeyStore, "keyStore is required");
		Assert.hasText(alias, "alias is required");

		try {
			return loadedKeyStore.containsAlias(alias);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
