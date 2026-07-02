package com.github.yingzhuo.turbocharger.util;

import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

public final class NumberParsingUtils {

	private NumberParsingUtils() {
		super();
	}

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
