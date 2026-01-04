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

import com.github.yingzhuo.turbocharger.util.text.StringMatcher;
import com.github.yingzhuo.turbocharger.util.text.StringTokenizer;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link StringTokenizer} 相关工具
 *
 * @author 应卓
 * @see StringTokenizer
 * @since 3.3.1
 */
public final class StringTokenizerUtils {

	/**
	 * 私有构造方法
	 */
	private StringTokenizerUtils() {
		super();
	}

	/**
	 * 拆分字符串
	 *
	 * @param text  字符串
	 * @param delim 分隔逻辑
	 * @return 拆分结果
	 */
	public static List<String> split(@Nullable String text, StringMatcher delim) {
		return split(text, delim, true, true);
	}

	/**
	 * 拆分字符串
	 *
	 * @param text             字符串
	 * @param delim            分隔逻辑
	 * @param trimToken        是否去掉每个token前后的白字符
	 * @param ignoreBlankToken 是否忽略空白的token
	 * @return 拆分结果
	 */
	public static List<String> split(@Nullable String text, StringMatcher delim, boolean trimToken, boolean ignoreBlankToken) {
		if (text == null) {
			return new ArrayList<>(0);
		}

		var list = new ArrayList<String>();
		var tokens = new StringTokenizer(text, delim);
		while (tokens.hasNext()) {
			var token = tokens.next();
			if (ignoreBlankToken) {
				if (StringUtils.isBlank(token)) {
					continue;
				}
			}

			if (trimToken) {
				token = token.trim();
			}

			list.add(token);
		}
		return list;
	}

}
