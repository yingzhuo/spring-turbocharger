/*
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
 */
package com.github.yingzhuo.turbocharger.util.time;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * {@link LocalDateTime} 相关工具
 *
 * @author 应卓
 * @since 3.2.6
 */
public final class LocalDateTimeUtils {

	/**
	 * 私有构造方法
	 */
	private LocalDateTimeUtils() {
	}

	/**
	 * {@link Date} 转换成 {@link LocalDateTime}
	 *
	 * @param date   日期
	 * @param zoneId 时区
	 * @return 转换结果
	 */
	public static LocalDateTime toLocalDateTime(Date date, @Nullable ZoneId zoneId) {
		Assert.notNull(date, "date is required");
		return Instant.ofEpochMilli(date.getTime())
			.atZone(Objects.requireNonNullElse(zoneId, ZoneId.systemDefault()))
			.toLocalDateTime();
	}

	/**
	 * 时间戳转换成 {@link LocalDateTime}
	 *
	 * @param timestamp 日期
	 * @param zoneId    时区
	 * @return 转换结果
	 */
	public static LocalDateTime toLocalDateTime(long timestamp, @Nullable ZoneId zoneId) {
		Assert.isTrue(timestamp >= 0, "timestamp should >= 0");

		return Instant.ofEpochMilli(timestamp)
			.atZone(Objects.requireNonNullElse(zoneId, ZoneId.systemDefault()))
			.toLocalDateTime();
	}

}
