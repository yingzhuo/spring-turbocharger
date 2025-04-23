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
package com.github.yingzhuo.turbocharger.util;

import java.util.HexFormat;

/**
 * HEX相关工具
 *
 * @author 应卓
 * @see Base64Utils
 * @since 1.0.0
 */
public final class HexUtils {

	/**
	 * 私有构造方法
	 */
	private HexUtils() {
	}

	/**
	 * bytes用hex方式表示
	 *
	 * @param bytes bytes
	 * @return bytes
	 */
	public static String encodeToString(byte[] bytes) {
		var format = HexFormat.of();
		return format.formatHex(bytes);
	}

	/**
	 * hex字符串转换成 bytes
	 *
	 * @param hexString hex字符串
	 * @return bytes
	 */
	public static byte[] decodeToBytes(String hexString) {
		var format = HexFormat.of();
		return format.parseHex(hexString);
	}

}
