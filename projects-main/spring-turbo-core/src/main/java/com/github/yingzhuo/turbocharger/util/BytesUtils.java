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

import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 3.5.3
 */
public final class BytesUtils {

	/**
	 * 私有构造方法
	 */
	private BytesUtils() {
		super();
	}

	/**
	 * 拼接多个字节数组
	 *
	 * @param bytesArray 字节数组的数组
	 * @return 拼接结果
	 */
	public static byte[] concat(@Nullable byte[]... bytesArray) {
		if (bytesArray == null || bytesArray.length == 0) {
			return new byte[0];
		}

		var count = Stream.of(bytesArray)
			.map(it -> it.length)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		Stream.of(bytesArray)
			.forEach(buffer::put);
		return buffer.array();
	}

	/**
	 * 拼接多个字节数组
	 *
	 * @param bytesCollection 字节数组的数组
	 * @return 拼接结果
	 */
	public static byte[] concat(@Nullable Collection<byte[]> bytesCollection) {
		if (bytesCollection == null || bytesCollection.isEmpty()) {
			return new byte[0];
		}

		var count = Stream.of(bytesCollection)
			.map(Collection::size)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		bytesCollection.forEach(buffer::put);
		return buffer.array();
	}

}
