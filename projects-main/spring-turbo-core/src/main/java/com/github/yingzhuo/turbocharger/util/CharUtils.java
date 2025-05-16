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

/**
 * 字符相关工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class CharUtils {

	/**
	 * 私有构造方法
	 */
	private CharUtils() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 判断字符是否为 {@code null}
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isNull(@Nullable Character ch) {
		return ch == null;
	}

	/**
	 * 判断字符是否不为 {@code null}
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isNotNull(@Nullable Character ch) {
		return ch != null;
	}

	/**
	 * 判断字符是否为白字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isWhitespace(@Nullable Character ch) {
		return ch != null && Character.isWhitespace(ch);
	}

	/**
	 * 判断字符是否不为白字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isNotWhitespace(@Nullable Character ch) {
		return !isWhitespace(ch);
	}

	/**
	 * 判断字符是否为Ascii字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAscii(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch < 128;
	}

	/**
	 * 判断字符是否为Ascii可打印字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiPrintable(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch >= 32 && ch < 127;
	}

	/**
	 * 判断字符是否为Ascii控制字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiControl(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch < 32 || ch == 127;
	}

	/**
	 * 判断字符是否为字母表中的字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiAlpha(@Nullable Character ch) {
		if (ch == null)
			return false;
		return isAsciiAlphaUpper(ch) || isAsciiAlphaLower(ch);
	}

	/**
	 * 判断字符是否为字母表中的字符并且为大写字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiAlphaUpper(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch >= 'A' && ch <= 'Z';
	}

	/**
	 * 判断字符是否为字母表中的字符并且为小写字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiAlphaLower(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch >= 'a' && ch <= 'z';
	}

	/**
	 * 判断字符是否为数字字符
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiNumeric(@Nullable Character ch) {
		if (ch == null)
			return false;
		return ch >= '0' && ch <= '9';
	}

	/**
	 * 判断字符是否为数字字符或字母
	 *
	 * @param ch 字符
	 * @return 判断结果
	 */
	public static boolean isAsciiAlphanumeric(@Nullable Character ch) {
		if (ch == null)
			return false;
		return isAsciiAlpha(ch) || isAsciiNumeric(ch);
	}

}
