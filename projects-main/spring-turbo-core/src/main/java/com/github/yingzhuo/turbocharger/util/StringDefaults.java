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

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * {@link String} 默认值相关工具
 *
 * @author 应卓
 * @see StringUtils
 * @since 2.1.0
 */
public final class StringDefaults {

	/**
	 * 私有构造方法
	 */
	private StringDefaults() {
		super();
	}

	public static String nullToDefault(@Nullable String string, String defaultString) {
		return string == null ? defaultString : string;
	}

	public static String emptyToDefault(@Nullable String string, String defaultString) {
		return string == null || string.isEmpty() ? defaultString : string;
	}

	public static String blankToDefault(@Nullable String string, String defaultString) {
		return string == null || string.isBlank() ? defaultString : string;
	}

	public static String nullToEmpty(@Nullable String string) {
		return nullToDefault(string, EMPTY);
	}

	public static String blankToEmpty(@Nullable String string) {
		return blankToDefault(string, EMPTY);
	}

}
