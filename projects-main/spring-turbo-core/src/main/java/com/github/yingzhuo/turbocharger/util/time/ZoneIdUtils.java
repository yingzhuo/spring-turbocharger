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

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.github.yingzhuo.turbocharger.util.StringUtils.isBlank;

/**
 * {@link ZoneId} 相关工具
 *
 * @author 应卓
 * @see ZoneId#getAvailableZoneIds()
 * @since 1.1.3
 */
public final class ZoneIdUtils {

	// SYSTEM
	public static final ZoneId SYSTEM_DEFAULT = ZoneId.systemDefault();

	// UTC
	public static final String EUROPE_LONDON_VALUE = "Europe/London";
	public static final ZoneId EUROPE_LONDON = ZoneId.of(EUROPE_LONDON_VALUE);

	// 中国
	public static final String ASIA_SHANGHAI_VALUE = "Asia/Shanghai";
	public static final ZoneId ASIA_SHANGHAI = ZoneId.of(ASIA_SHANGHAI_VALUE);

	/**
	 * 私有构造方法
	 */
	private ZoneIdUtils() {
	}

	public static ZoneId toZoneIdOrSystemDefault(@Nullable String name) {
		var id = toZoneIdOrDefault(name, SYSTEM_DEFAULT);
		return Objects.requireNonNull(id);
	}

	@Nullable
	public static ZoneId toZoneIdOrDefault(@Nullable String name, @Nullable ZoneId defaultIfNullOrError) {

		if (isBlank(name)) {
			return defaultIfNullOrError;
		}

		try {
			return ZoneId.of(name);
		} catch (DateTimeException e) {
			var matcher = Pattern.compile("^([a-zA-Z]+[+-]\\d+)\\.0+$").matcher(name);
			if (matcher.matches()) {
				name = matcher.replaceAll("$1");
				try {
					return ZoneId.of(name);
				} catch (Throwable ex) {
					return defaultIfNullOrError;
				}
			} else {
				return defaultIfNullOrError;
			}
		}
	}

}
