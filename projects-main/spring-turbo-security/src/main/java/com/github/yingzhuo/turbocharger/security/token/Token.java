/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.token;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * 令牌
 *
 * @author 应卓
 * @since 1.0.0
 */
@FunctionalInterface
public interface Token extends Comparable<Token>, Supplier<String>, Serializable {

	/**
	 * 获取令牌的值
	 *
	 * @return 令牌的值
	 */
	@NonNull
	public String asString();

	/**
	 * 获取令牌的值
	 *
	 * @return 令牌的值
	 */
	@Override
	public default String get() {
		return this.asString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public default int compareTo(Token o) {
		return this.asString().compareTo(o.asString());
	}

}
