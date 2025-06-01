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

import com.github.yingzhuo.turbocharger.util.Base64Utils;
import com.github.yingzhuo.turbocharger.util.StringPool;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * HTTP Basic 令牌解析器
 *
 * @author 应卓
 * @see HeaderTokenResolver
 * @see BearerTokenResolver
 * @see HttpHeaders#AUTHORIZATION
 * @since 1.0.5
 */
public final class BasicTokenResolver extends HeaderTokenResolver {

	private static final String PREFIX = "Basic ";

	/**
	 * 构造方法
	 */
	public BasicTokenResolver() {
		super(HttpHeaders.AUTHORIZATION, PREFIX);
	}

	@Override
	public Optional<Token> resolve(NativeWebRequest request) {
		final Optional<Token> tokenOption = super.resolve(request);

		if (tokenOption.isEmpty()) {
			return tokenOption;
		}

		final String tokenValue = tokenOption.get().asString();
		String headerValue = tokenValue;
		headerValue = new String(Base64Utils.decode(headerValue.getBytes(UTF_8)), UTF_8);

		final String[] parts = headerValue.split(StringPool.COLON);
		if (parts.length != 2) {
			return Optional.empty();
		} else {
			return Optional.of(new BasicToken(tokenValue, parts[0], parts[1]));
		}
	}

}
