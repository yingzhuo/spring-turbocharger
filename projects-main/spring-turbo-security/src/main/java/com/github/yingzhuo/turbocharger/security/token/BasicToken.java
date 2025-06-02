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

import lombok.Getter;

/**
 * @author 应卓
 * @see BasicTokenResolver
 * @since 1.2.3
 */
public class BasicToken extends StringToken {

	/**
	 * 用户名
	 */
	@Getter
	private final String username;

	/**
	 * 口令
	 */
	@Getter
	private final String password;

	/**
	 * 构造方法
	 *
	 * @param stringValue 令牌原字符串
	 * @param username    用户名
	 * @param password    口令
	 */
	public BasicToken(String stringValue, String username, String password) {
		super(stringValue);
		this.username = username;
		this.password = password;
	}

}
