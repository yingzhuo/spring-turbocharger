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
package com.github.yingzhuo.turbocharger.security.token;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 令牌
 *
 * @author 应卓
 * @see #ofString(CharSequence)
 * @since 1.0.0
 */
@FunctionalInterface
public interface Token extends Serializable {

	/**
	 * 创建 {@link StringToken}
	 *
	 * @param stringValue 令牌
	 * @return 实例
	 */
	public static Token ofString(CharSequence stringValue) {
		Assert.notNull(stringValue, "stringValue must not be null");
		return StringToken.of(stringValue.toString());
	}

	/**
	 * 获取令牌的值
	 *
	 * @return 令牌的值
	 */
	public String asString();

}
