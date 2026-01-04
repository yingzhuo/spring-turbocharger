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
package com.github.yingzhuo.turbocharger.util.time;

import org.springframework.boot.convert.DurationStyle;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * {@link Duration} 格式化工具
 *
 * @author 应卓
 * @see DurationParseUtils
 * @since 1.0.1
 */
public final class DurationFormatUtils {

	/**
	 * 私有构造方法
	 */
	private DurationFormatUtils() {
		super();
	}

	/**
	 * 格式化 {@link Duration}实例
	 *
	 * @param duration {@link Duration}实例
	 * @return 格式化字符串
	 */
	public static String format(Duration duration) {
		return format(duration, null);
	}

	/**
	 * 格式化 {@link Duration}实例
	 *
	 * @param duration {@link Duration}实例
	 * @param unit     默认时间单位，可为空，为空时为毫秒
	 * @return 格式化字符串
	 */
	public static String format(Duration duration, @Nullable ChronoUnit unit) {
		Assert.notNull(duration, "duration is required");
		return DurationStyle.ISO8601.print(duration, unit);
	}

}
