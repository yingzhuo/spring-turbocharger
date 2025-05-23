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
package com.github.yingzhuo.turbocharger.util.crypto.bundle;

import com.github.yingzhuo.turbocharger.util.crypto.CipherUtils;
import com.github.yingzhuo.turbocharger.util.crypto.SignatureUtils;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * 对非对称加密算法秘钥和证书的封装
 *
 * @author 应卓
 * @see SignatureUtils
 * @see CipherUtils
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
public interface KeyBundle extends Serializable {

	/**
	 * 获取KeyPair
	 *
	 * @return KeyPair实例
	 */
	public KeyPair getKeyPair();

	/**
	 * 获取证书
	 *
	 * @param <T> 证书泛型类型
	 * @return 证书
	 */
	public <T extends X509Certificate> T getCertificate();

	/**
	 * 获取公钥
	 *
	 * @return 公钥实例
	 */
	public default <T extends PublicKey> T getPublicKey() {
		return (T) getKeyPair().getPublic();
	}

	/**
	 * 获取私钥
	 *
	 * @return 私钥实例
	 */
	public default <T extends PrivateKey> T getPrivateKey() {
		return (T) getKeyPair().getPrivate();
	}

	/**
	 * 获取算法名称
	 *
	 * @return 算法名称 如: RSA
	 */
	public default String getAlgorithm() {
		return getCertificate().getPublicKey().getAlgorithm();
	}

	/**
	 * 获取签名算法名称
	 *
	 * @return 签名算法名称 如: SHA256WithRSA
	 */
	public default String getSigAlgName() {
		return getCertificate().getSigAlgName();
	}

}
