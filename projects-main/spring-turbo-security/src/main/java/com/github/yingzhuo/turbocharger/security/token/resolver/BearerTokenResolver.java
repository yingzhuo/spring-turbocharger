/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.security.token.resolver;

import org.springframework.http.HttpHeaders;

/**
 * HTTP Bearer 令牌解析器
 *
 * @author 应卓
 * @see HeaderTokenResolver
 * @see HttpHeaders#AUTHORIZATION
 * @since 1.0.5
 */
public class BearerTokenResolver extends HeaderTokenResolver {

	private static final String PREFIX = "Bearer ";

	/**
	 * 默认构造方法
	 */
	public BearerTokenResolver() {
		super(HttpHeaders.AUTHORIZATION, PREFIX);
	}

}
