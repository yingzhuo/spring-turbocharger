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
package com.github.yingzhuo.turbocharger.security.token.resolver;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.github.yingzhuo.turbocharger.util.collection.CollectionUtils.nullSafeAddAll;

/**
 * 令牌解析器的创建器
 *
 * @author 应卓
 * @see TokenResolver
 * @since 1.0.0
 */
public final class TokenResolverBuilder {

	private final List<TokenResolver> list = new ArrayList<>();

	/**
	 * 构造方法
	 */
	TokenResolverBuilder() {
		super();
	}

	/**
	 * 添加其他令牌解析器的实现
	 *
	 * @param resolvers 要添加的解析器
	 * @return 创建器本身
	 */
	public TokenResolverBuilder add(TokenResolver... resolvers) {
		nullSafeAddAll(list, resolvers);
		return this;
	}

	/**
	 * 添加其他令牌解析器的实现
	 *
	 * @param resolvers 要添加的解析器
	 * @return 创建器本身
	 */
	public TokenResolverBuilder add(Collection<TokenResolver> resolvers) {
		nullSafeAddAll(list, resolvers);
		return this;
	}

	/**
	 * 从HTTP请求头中解析令牌
	 *
	 * @param headName 请求头名称
	 * @return 创建器本身
	 * @see HeaderTokenResolver
	 */
	public TokenResolverBuilder fromHttpHeader(String headName) {
		list.add(new HeaderTokenResolver(headName));
		return this;
	}

	/**
	 * 从HTTP请求头中解析令牌
	 *
	 * @param headName 请求头名称
	 * @param prefix   前缀
	 * @return 创建器本身
	 * @see HeaderTokenResolver
	 */
	public TokenResolverBuilder fromHttpHeader(String headName, @Nullable String prefix) {
		list.add(new HeaderTokenResolver(headName, prefix));
		return this;
	}

	/**
	 * 从HTTP Query中解析令牌
	 *
	 * @param paramName query名称
	 * @return 创建器本身
	 */
	public TokenResolverBuilder fromHttpQuery(String paramName) {
		list.add(new QueryTokenResolver(paramName));
		return this;
	}

	/**
	 * 从HTTP Query中解析令牌
	 *
	 * @param paramName query名称
	 * @param prefix    前缀
	 * @return 创建器本身
	 */
	public TokenResolverBuilder fromHttpQuery(String paramName, @Nullable String prefix) {
		list.add(new QueryTokenResolver(paramName, prefix));
		return this;
	}

	/**
	 * Bearer 令牌解析器
	 *
	 * @return 创建器本身
	 */
	public TokenResolverBuilder bearerToken() {
		list.add(new BearerTokenResolver());
		return this;
	}

	/**
	 * 创建令牌解析器
	 *
	 * @return 解析器实例
	 */
	public TokenResolver build() {
		if (list.isEmpty()) {
			return request -> Optional.empty();
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return new CompositeTokenResolver(list);
	}

}
