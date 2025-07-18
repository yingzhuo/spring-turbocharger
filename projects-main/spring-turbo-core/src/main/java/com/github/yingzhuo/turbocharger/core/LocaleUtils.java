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
package com.github.yingzhuo.turbocharger.core;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * {@link Locale} 相关工具
 *
 * @author 应卓
 * @since 3.3.1
 */
public final class LocaleUtils {

	/**
	 * 私有构造方法
	 */
	private LocaleUtils() {
		super();
	}

	/**
	 * 获取{@link Locale} 实例。会强制去掉变体部分
	 *
	 * @return 实例
	 */
	public static Locale getLocale() {
		return getLocale(true);
	}

	/**
	 * 获取{@link Locale} 实例
	 *
	 * @param removeVariant 是否强制去掉变体部分
	 * @return 实例
	 */
	public static Locale getLocale(boolean removeVariant) {
		Locale locale;
		try {
			locale = LocaleContextHolder.getLocale();
		} catch (Throwable e) {
			locale = Locale.getDefault();
		}

		if (removeVariant) {
			var lang = locale.getLanguage();
			var country = locale.getCountry();

			if (lang != null && country != null) {
				return new Locale(lang, country);
			} else if (lang != null) {
				return new Locale(lang);
			} else {
				return Locale.getDefault();
			}
		} else {
			return locale;
		}
	}

}
