/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link CharSequence} 相关工具
 *
 * @author 应卓
 * @since 2.0.1
 */
public final class CharSequenceUtils {

	/**
	 * 私有构造方法
	 */
	private CharSequenceUtils() {
		super();
	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * 获取字符序列的长度
	 *
	 * @param cs 字符序列或 {@code null}
	 * @return 结果
	 */
	public static int length(@Nullable CharSequence cs) {
		return cs != null ? cs.length() : 0;
	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * 获取子字符序列
	 *
	 * @param cs    指定的字符序列
	 * @param start 切割开始位置 (包含)
	 * @return a new subsequence, may be null
	 * @throws IndexOutOfBoundsException start 参数不合法
	 */
	public static CharSequence subSequence(CharSequence cs, int start) {
		Assert.notNull(cs, "cs is required");
		return cs.subSequence(start, cs.length());
	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * {@link CharSequence} 转换成 char[]
	 *
	 * @param source 指定 {@code CharSequence} 的实例.
	 * @return 结果
	 */
	public static char[] toCharArray(@Nullable CharSequence source) {
		if (source == null) {
			return new char[0];
		}

		int len = StringUtils.length(source.toString());

		if (len == 0) {
			return new char[0];
		}

		if (source instanceof String) {
			return ((String) source).toCharArray();
		}

		char[] array = new char[len];

		for (int i = 0; i < len; i++) {
			array[i] = source.charAt(i);
		}
		return array;
	}

}
