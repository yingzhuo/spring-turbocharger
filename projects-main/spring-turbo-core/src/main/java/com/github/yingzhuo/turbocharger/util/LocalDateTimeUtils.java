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

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 应卓
 * @since 4.1.0
 */
public final class LocalDateTimeUtils {

	public static final String[] TRYING_DATE_TIME_CONVERSION_PATTERNS = {
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd HH:mm:ss.SSS",
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd HH:mm:ss.SSS",
		"yyyy-MM-dd'T'HH:mm:ss",
		"yyyy-MM-dd'T'HH:mm:ss.SSS"
	};

	private static final DateTimeFormatter LONG_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter SHORT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 私有构造方法
	 */
	private LocalDateTimeUtils() {
		super();
	}

	/**
	 * 格式化日期时间 yyyy-MM-dd
	 *
	 * @param localDateTime 日期时间
	 * @return 格式化结果
	 */
	public static String formatToShort(LocalDateTime localDateTime) {
		Assert.notNull(localDateTime, "localDateTime must not be null");
		return localDateTime.format(SHORT_FORMATTER);
	}

	/**
	 * 格式化日期时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param localDateTime 日期时间
	 * @return 格式化结果
	 */
	public static String formatToLong(LocalDateTime localDateTime) {
		Assert.notNull(localDateTime, "localDateTime must not be null");
		return localDateTime.format(LONG_FORMATTER);
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 从文本反序列化
	 *
	 * @param text 文本
	 * @return 转换结果
	 * @throws DateTimeParseException 多方尝试最终无法转换成功
	 */
	public static LocalDateTime convertFromString(String text) {
		return convertFromString(text, TRYING_DATE_TIME_CONVERSION_PATTERNS);
	}

	/**
	 * 从文本反序列化
	 *
	 * @param text     文本
	 * @param patterns 尝试转换的日期格式
	 * @return 转换结果
	 * @throws DateTimeParseException 多方尝试最终无法转换成功
	 */
	public static LocalDateTime convertFromString(String text, @Nullable String... patterns) {
		Assert.hasText(text, "text must not be empty");

		patterns = Objects.requireNonNullElse(patterns, new String[0]);

		var formatters = Arrays.stream(patterns)
			.map(DateTimeFormatter::ofPattern)
			.toList();

		return convertFromString(text, formatters);
	}

	/**
	 * 从文本反序列化
	 *
	 * @param text       文本
	 * @param formatters 需要尝试的若干formatter
	 * @return 转换结果
	 * @throws DateTimeParseException 多方尝试最终无法转换成功
	 */
	public static LocalDateTime convertFromString(String text, @Nullable List<DateTimeFormatter> formatters) {
		Assert.hasText(text, "text must not be empty");

		formatters = Objects.requireNonNullElse(formatters, List.of());

		try {
			Long timestamp = Long.parseLong(text);
			if (timestamp.toString().length() == 10) {
				return LocalDateTime.ofInstant(
					Instant.ofEpochSecond(timestamp),
					ZoneId.systemDefault()
				);
			} else {
				return LocalDateTime.ofInstant(
					Instant.ofEpochMilli(timestamp),
					ZoneId.systemDefault()
				);
			}
		} catch (NumberFormatException ignored) {
			// 不是整型数
		}

		for (var formatter : formatters) {
			try {
				return LocalDateTime.parse(text, formatter);
			} catch (DateTimeParseException ignored) {
				// 一个不行就尝试下一个
			}
		}

		throw new DateTimeParseException("Cannot parse text to LocalDateTime", text, 0);
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 计算两个日期的年份差距 可用来计算年龄等。<br>
	 * 最后会取绝对值，第一参数和第二参数的顺序不重要。
	 *
	 * @param d1 第一个日期
	 * @param d2 第二个日期
	 * @return 结果
	 */
	public static long getYearDistance(LocalDateTime d1, LocalDateTime d2) {
		Assert.notNull(d1, "d1 must not be null");
		Assert.notNull(d2, "d2 must not be null");
		return Math.abs(ChronoUnit.YEARS.between(d1, d2));
	}

	/**
	 * 计算两个日期的天的差距
	 *
	 * @param d1 第一个日期
	 * @param d2 第二个日期
	 * @return 结果
	 */
	public static long getDayDistance(LocalDateTime d1, LocalDateTime d2) {
		Assert.notNull(d1, "d1 must not be null");
		Assert.notNull(d2, "d2 must not be null");
		return Math.abs(ChronoUnit.DAYS.between(d1, d2));
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 计算年龄
	 *
	 * @param dob 出生日期
	 * @return 年龄 或 {@code null}
	 */
	@Nullable
	public static Integer getAge(@Nullable LocalDateTime dob) {
		if (dob == null) {
			return null;
		}
		return (int) getYearDistance(dob, LocalDateTime.now());
	}

	/**
	 * 计算年龄
	 *
	 * @param dob 出生日期
	 * @return 年龄 或 {@code null}
	 */
	@Nullable
	public static Integer getAge(@Nullable LocalDate dob) {
		if (dob == null) {
			return null;
		}
		return getAge(dob.atStartOfDay());
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 截断到零点
	 *
	 * @param dateTime 日期
	 * @return 截断后的日期
	 */
	public static LocalDateTime toThisDayMidnight(LocalDateTime dateTime) {
		Assert.notNull(dateTime, "dateTime must not be null");
		return dateTime.truncatedTo(ChronoUnit.DAYS);
	}

	/**
	 * 到下一天午夜
	 *
	 * @param dateTime 日期
	 * @return 一下天午夜
	 */
	public static LocalDateTime toNextDayMidnight(LocalDateTime dateTime) {
		Assert.notNull(dateTime, "dateTime must not be null");
		return dateTime.plusDays(1).truncatedTo(ChronoUnit.DAYS);
	}

}
