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

import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * 从HTTP QUERY中解析令牌
 *
 * @author 应卓
 * @since 1.0.0
 */
public class QueryTokenResolver implements TokenResolver {

	protected final String paramName;
	protected final String prefix;
	protected final int prefixLen;

	/**
	 * 构造方法
	 *
	 * @param paramName query name
	 */
	public QueryTokenResolver(@NonNull String paramName) {
		this(paramName, EMPTY);
	}

	/**
	 * 构造方法
	 *
	 * @param paramName query name
	 * @param prefix    前缀
	 */
	public QueryTokenResolver(@NonNull String paramName, @Nullable String prefix) {
		if (prefix == null)
			prefix = EMPTY;
		this.paramName = paramName;
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
	public Optional<Token> resolve(NativeWebRequest request) {
		String paramValue = request.getParameter(paramName);

		if (paramValue == null || !paramValue.startsWith(prefix)) {
			return Optional.empty();
		}

		paramValue = paramValue.substring(prefixLen);

		if (StringUtils.isBlank(paramValue)) {
			return Optional.empty();
		}

		return Optional.of(StringToken.of(paramValue));
	}

	/**
	 * 排序参数
	 *
	 * @return 排序值
	 */
	@Override
	public int getOrder() {
		return -100;
	}

	public String getParamName() {
		return paramName;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getPrefixLen() {
		return prefixLen;
	}

}
