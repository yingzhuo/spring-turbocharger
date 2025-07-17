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
package com.github.yingzhuo.turbocharger.security.token.resolver;

import com.github.yingzhuo.turbocharger.security.token.StringToken;
import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * 通过HTTP header解析令牌
 *
 * @author 应卓
 * @see org.springframework.http.HttpHeaders
 * @see QueryTokenResolver
 * @since 1.0.0
 */
public class HeaderTokenResolver implements TokenResolver {

	protected final String headerName;
	protected final String prefix;
	protected final int prefixLen;

	/**
	 * 构造方法
	 *
	 * @param headerName 请求头名
	 */
	public HeaderTokenResolver(String headerName) {
		this(headerName, null);
	}

	/**
	 * 构造方法
	 *
	 * @param headerName 请求头名
	 * @param prefix     前缀
	 */
	public HeaderTokenResolver(String headerName, @Nullable String prefix) {
		Assert.hasText(headerName, "headerName is required");

		prefix = Objects.requireNonNullElse(prefix, "");

		this.headerName = headerName;
		this.prefix = prefix;
		this.prefixLen = prefix.length();
	}

	/**
	 * 解析令牌
	 *
	 * @param request HTTP请求
	 * @return 令牌Optional，不能成功解析时返回empty-optional
	 */
	@Override
	public Optional<Token> resolve(WebRequest request) {
		var headerValue = request.getHeader(headerName);

		if (headerValue == null || !headerValue.startsWith(prefix)) {
			return Optional.empty();
		}

		headerValue = headerValue.substring(prefixLen);

		if (StringUtils.isBlank(headerValue)) {
			return Optional.empty();
		}

		return Optional.of(new StringToken(headerValue));
	}

}
