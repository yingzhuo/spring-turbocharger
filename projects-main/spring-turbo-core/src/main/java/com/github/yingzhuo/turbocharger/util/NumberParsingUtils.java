/*
 * Copyright 2022-2025 the original author or authors.
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

import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * 字符串到数字解析工具
 *
 * @author 应卓
 * @since 1.0.8
 */
public final class NumberParsingUtils {

	/**
	 * 私有构造方法
	 */
	private NumberParsingUtils() {
		super();
	}

	/**
	 * 从字符串中解析数字
	 * <ul>
	 * <li>支持科学计数法</li>
	 * <li>支持十六进制数</li>
	 * </ul>
	 * 请正确使用类型，否则会有精度损失
	 *
	 * @param text         字符串
	 * @param typeOfNumber 具体类型
	 * @param <T>          数字的具体类型泛型
	 * @return 结果
	 * @throws IllegalArgumentException 解析失败
	 */
	public static <T extends Number> T parse(String text, Class<T> typeOfNumber) {
		Assert.notNull(text, "text is required");
		Assert.notNull(typeOfNumber, "type is required");

		// 移除白字符和逗号
		text = text.replaceAll("[\\s,]", EMPTY);

		// 十六进制数特殊处理
		if (text.startsWith("#") || text.startsWith("-#") || text.startsWith("0x") || text.startsWith("0X")
			|| text.startsWith("-0x") || text.startsWith("-0X")) {
			final BigInteger bigInteger = NumberUtils.parseNumber(text, BigInteger.class);
			return BigDecimalUtils.getValue(new BigDecimal(bigInteger), typeOfNumber);
		}

		// 科学计数法特殊处理
		if (text.contains("E") || text.contains("e")) {
			final BigDecimal bigDecimal = NumberUtils.parseNumber(text, BigDecimal.class);
			return BigDecimalUtils.getValue(bigDecimal, typeOfNumber);
		}

		try {
			return NumberUtils.parseNumber(text, typeOfNumber);
		} catch (IllegalArgumentException e) {
			return fallback(text, typeOfNumber);
		}
	}

	private static <T extends Number> T fallback(String text, Class<T> type) {
		BigDecimal big;

		try {
			big = new BigDecimal(text);
		} catch (Throwable e) {
			throw new IllegalArgumentException(getErrorMessage(text));
		}

		return BigDecimalUtils.getValue(big, type);
	}

	private static String getErrorMessage(String text) {
		return StringFormatter.format("{} is not a valid number", text);
	}

}
