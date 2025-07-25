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
package com.github.yingzhuo.turbocharger.util.time;

import org.springframework.boot.convert.DurationStyle;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * {@link Duration} 解析工具
 *
 * @author 应卓
 * @see DurationFormatUtils
 * @since 1.0.1
 */
public final class DurationParseUtils {

	/**
	 * 私有构造方法
	 */
	private DurationParseUtils() {
	}

	/**
	 * 从字符串解析 {@link Duration}
	 * <p>
	 * 用法:
	 * <ul>
	 * <li>{@code DurationParseUtils.parse("200ms")}</li>
	 * <li>{@code DurationParseUtils.parse("1h")}</li>
	 * <li>{@code DurationParseUtils.parse("P21D")}</li>
	 * <li>{@code DurationParseUtils.parse("P3Y6M")}</li>
	 * </ul>
	 *
	 * @param string 待解析的字符串
	 * @return 解析结果
	 * @throws IllegalArgumentException 字符串格式不正确
	 */
	public static Duration parse(String string) {
		return parse(string, null);
	}

	/**
	 * 从字符串解析 {@link Duration}
	 * <p>
	 * 用法:
	 * <ul>
	 * <li>{@code DurationParseUtils.parse("200ms", ChronoUnit.MILLIS)}</li>
	 * <li>{@code DurationParseUtils.parse("1h", ChronoUnit.MILLIS)}</li>
	 * <li>{@code DurationParseUtils.parse("P21D", null)}</li>
	 * <li>{@code DurationParseUtils.parse("P3Y6M", null)}</li>
	 * </ul>
	 *
	 * @param string 待解析的字符串
	 * @param unit   默认时间单位，可为空，为空时为毫秒
	 * @return 解析结果
	 * @throws IllegalArgumentException 字符串格式不正确
	 */
	public static Duration parse(@NonNull String string, @Nullable ChronoUnit unit) {
		Assert.hasText(string, "string is required");

		Duration duration = parseFromSimpleStyle(string, unit);
		if (duration == null) {
			duration = parseFromISO8601Style(string, unit);
		}

		if (duration == null) {
			throw new IllegalArgumentException("'" + string + "' is not a valid duration");
		}

		return duration;
	}

	@Nullable
	private static Duration parseFromSimpleStyle(String string, @Nullable ChronoUnit unit) {
		try {
			return DurationStyle.SIMPLE.parse(string, unit);
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	private static Duration parseFromISO8601Style(String string, @Nullable ChronoUnit unit) {
		try {
			return DurationStyle.ISO8601.parse(string, unit);
		} catch (Exception e) {
			return null;
		}
	}

}
