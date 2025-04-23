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
package com.github.yingzhuo.turbocharger.util;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * 类型转换工具
 *
 * @author 应卓
 * @since 3.2.5
 */
@SuppressWarnings("unchecked")
public final class CastUtils {

	/**
	 * 私有构造方法
	 */
	private CastUtils() {
	}

	@Nullable
	public static <T> T castNullable(@Nullable Object object) {
		return (T) object;
	}

	public static <T> T castNonNull(Object object) {
		Assert.notNull(object, "object is required");
		return (T) object;
	}

}
