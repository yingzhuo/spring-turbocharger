/*
 *
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
 *
 */

package com.github.yingzhuo.turbocharger.util.collection;

import org.springframework.lang.Nullable;

import static com.github.yingzhuo.turbocharger.util.collection.ArrayUtils.isNullOrEmpty;

/**
 * 数组默认值相关工具
 *
 * @author 应卓
 * @see ArrayUtils
 * @since 2.1.0
 */
public final class ArrayDefaults {

	/**
	 * 私有构造方法
	 */
	private ArrayDefaults() {
	}

	public static <T> T[] nullToDefault(@Nullable T[] array, T[] defaultArray) {
		return array == null ? defaultArray : array;
	}

	public static <T> T[] emptyToDefault(@Nullable T[] array, T[] defaultArray) {
		return isNullOrEmpty(array) ? defaultArray : array;
	}

}
