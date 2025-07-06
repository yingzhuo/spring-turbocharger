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
package com.github.yingzhuo.turbocharger.util.crypto;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * 加密工具
 *
 * @author 应卓
 * @since 3.3.1
 */
@Deprecated(forRemoval = true, since = "3.5.3")
public final class CipherUtils {

	/**
	 * 私有构造方法
	 */
	private CipherUtils() {
		super();
	}

	/**
	 * 加密
	 *
	 * @param rawData 要加密的数据
	 * @param key     加密秘钥
	 * @return 加密结果
	 */
	public static byte[] encrypt(byte[] rawData, Key key) {
		try {
			var cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(rawData);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 解密
	 *
	 * @param encryptedData 已加密的数据
	 * @param key           解密秘钥
	 * @return 解密结果
	 */
	public static byte[] decrypt(byte[] encryptedData, Key key) {
		try {
			var cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
