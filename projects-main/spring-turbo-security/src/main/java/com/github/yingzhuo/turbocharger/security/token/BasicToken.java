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

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 应卓
 * @see BasicTokenResolver
 * @since 1.2.3
 */
public final class BasicToken implements Token, Serializable {

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
	 * 令牌原字符串
	 */
	private final String string;

	/**
	 * 构造方法
	 *
	 * @param stringValue 令牌原字符串
	 * @param username    用户名
	 * @param password    口令
	 */
	public BasicToken(String stringValue, String username, String password) {
		this.username = username;
		this.password = password;
		this.string = stringValue;
	}

	@Override
	public String asString() {
		return string;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BasicToken that = (BasicToken) o;
		return string.equals(that.string);
	}

	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

}
