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

import java.util.Objects;

/**
 * String令牌
 *
 * @author 应卓
 * @since 1.0.0
 */
public class StringToken implements Token {

	private final String string;

	public StringToken(String tokenValue) {
		this.string = tokenValue;
	}

	public static StringToken of(String token) {
		Assert.hasText("token", "token is null or blank");
		return new StringToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asString() {
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		StringToken that = (StringToken) o;
		return string.equals(that.string);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

}
