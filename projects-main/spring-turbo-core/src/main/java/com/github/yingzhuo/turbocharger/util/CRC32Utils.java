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

import java.util.zip.CRC32;

/**
 * CRC32相关工具
 *
 * @author 应卓
 * @since 3.3.5
 */
public final class CRC32Utils {

	/**
	 * 私有构造方法
	 */
	private CRC32Utils() {
	}

	/**
	 * 计算数据的CRC32累加和
	 *
	 * @param data 数据
	 * @return 累加和
	 */
	public static Long crc32Value(byte[] data) {
		var crc32 = new CRC32();
		crc32.update(data);
		return crc32.getValue();
	}

	/**
	 * 计算数据的CRC32累加和
	 *
	 * @param data 数据
	 * @return 累加和 (字符串表示)
	 */
	public static String crc32Hex(byte[] data) {
		var value = crc32Value(data);
		return Long.toHexString(value);
	}

}
