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
package com.github.yingzhuo.turbocharger.util.hash;

import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 基于信息摘要算法的哈希函数器
 *
 * @author 应卓
 * @since 3.4.0
 */
public class DigestHashFunction implements HashFunction {

	/**
	 * 算法名称
	 */
	private final String algName;

	/**
	 * 构造方法
	 *
	 * @param algName 算法名称
	 */
	public DigestHashFunction(String algName) {
		Assert.hasText(algName, "algName is null or null");
		this.algName = algName;
	}

	/**
	 * md5为基础的实例
	 *
	 * @return 哈希函数器
	 */
	public static HashFunction md5() {
		return SyncAvoid.MD5;
	}

	/**
	 * SHA-1为基础的实例
	 *
	 * @return 哈希函数器
	 */
	public static HashFunction sha1() {
		return SyncAvoid.SHA1;
	}

	/**
	 * SHA-256为基础的实例
	 *
	 * @return 哈希函数器
	 */
	public static HashFunction sha256() {
		return SyncAvoid.SHA256;
	}

	/**
	 * SHA-384为基础的实例
	 *
	 * @return 哈希函数器
	 */
	public static HashFunction sha384() {
		return SyncAvoid.SHA384;
	}

	/**
	 * SHA-512为基础的实例
	 *
	 * @return 哈希函数器
	 */
	public static HashFunction sha512() {
		return SyncAvoid.SHA512;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer apply(String key) {
		try {
			var digest = MessageDigest.getInstance(algName).digest(key.getBytes());
			// @formatter:off
            return ((digest[0] & 0XFF) << 24) |
                    ((digest[1] & 0XFF) << 16) |
                    ((digest[2] & 0XFF) << 8) |
                    (digest[3] & 0XFF);
            // @formatter:on
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(); // 实际方法不可能运行到此处
		}
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final HashFunction MD5 = new DigestHashFunction("MD5");
		private static final HashFunction SHA1 = new DigestHashFunction("SHA-1");
		private static final HashFunction SHA256 = new DigestHashFunction("SHA-256");
		private static final HashFunction SHA384 = new DigestHashFunction("SHA-384");
		private static final HashFunction SHA512 = new DigestHashFunction("SHA-512");
	}

}
