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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.0.10
 */
public final class DateParseUtils {

	private static final String PRIMARY_PATTERN = "yyyy-MM-dd";

	private static final String[] BACKUP_PATTERNS = new String[]{
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd HH:mm:ss.SSS",
		"yyyy-MM-dd",
		"yyyy-MM-dd'T'HH:mm:ss",
		"yyyy-MM-dd'T'HH:mm:ss.SSS",
		"yyyy-MM-dd",
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd HH:mm:ss.SSS",
		"yyyy/MM/dd",
		"yyyy-M-d HH:mm:ss",
		"yyyy-M-d HH:mm:ss.SSS",
		"yyyy-M-d",
		"yyyy/M/d HH:mm:ss",
		"yyyy/M/d HH:mm:ss.SSS",
		"yyyy/M/d",
		"yyyy",
		"yyyy-MM",
		"yyyy-M"
	};

	/**
	 * 私有构造方法
	 */
	private DateParseUtils() {
	}

	public static Date parse(String string, String pattern, String... fallbackPatterns) {
		Assert.hasText(string, "string ire required");
		Assert.hasText(pattern, "pattern is required");

		try {
			final DateFormatter formatter = new DateFormatter();
			formatter.setIso(DateTimeFormat.ISO.NONE);
			formatter.setPattern(pattern);
			formatter.setFallbackPatterns(fallbackPatterns);
			return formatter.parse(string, Locale.getDefault());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public static Date parse(String string, String pattern, @Nullable Collection<String> fallbackPatterns) {
		if (fallbackPatterns != null && !fallbackPatterns.isEmpty()) {
			return parse(string, pattern, fallbackPatterns.toArray(new String[0]));
		} else {
			return parse(string, pattern);
		}
	}

	public static Date parseWildly(String string) {
		return parse(string, PRIMARY_PATTERN, BACKUP_PATTERNS);
	}

}
