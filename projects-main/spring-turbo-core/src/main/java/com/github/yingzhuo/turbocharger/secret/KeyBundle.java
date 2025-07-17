/*
 * Copyright 2025-present the original author or authors.
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
 */
package com.github.yingzhuo.turbocharger.secret;

import com.github.yingzhuo.turbocharger.util.SignatureUtils;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * 对秘钥/密钥对的简易封装
 *
 * @author 应卓
 * @see KeyPair
 * @see SignatureUtils
 * @see KeyBundleFactories
 * @since 3.5.3
 */
public interface KeyBundle extends Serializable {

	/**
	 * 获取公钥
	 *
	 * @param <T> 公钥的具体类型
	 * @return 公钥
	 */
	public <T extends PublicKey> T getPublicKey();

	/**
	 * 获取私钥
	 *
	 * @param <T> 私钥的具体类型
	 * @return 私钥
	 */
	public <T extends PrivateKey> T getPrivateKey();

	/**
	 * 获取密钥对
	 *
	 * @return 秘钥对
	 */
	public default KeyPair getKeyPair() {
		return new KeyPair(getPublicKey(), getPrivateKey());
	}

	/**
	 * 获取证书
	 *
	 * @param <T> 证书的具体类型
	 * @return 证书
	 */
	public <T extends X509Certificate> T getCertificate();

	/**
	 * 获取证书链
	 *
	 * @return 证书链
	 */
	public List<X509Certificate> getCertificateChain();

}
