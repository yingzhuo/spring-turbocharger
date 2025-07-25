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

import com.github.yingzhuo.turbocharger.security.token.Token;
import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

/**
 * 组合型令牌解析器
 * <p>
 * 本类型解析器封装多个其他的解析器，如果之前的解析器不能解析出令牌，则尝试下一个。
 *
 * @author 应卓
 * @see TokenResolver#builder()
 * @since 1.0.0
 */
public final class CompositeTokenResolver implements TokenResolver, Iterable<TokenResolver> {

	private final List<TokenResolver> resolvers = new ArrayList<>();

	public CompositeTokenResolver(TokenResolver... resolvers) {
		CollectionUtils.nullSafeAddAll(this.resolvers, resolvers);
		OrderComparator.sort(this.resolvers);
	}

	public CompositeTokenResolver(Collection<TokenResolver> resolvers) {
		CollectionUtils.nullSafeAddAll(this.resolvers, resolvers);
		OrderComparator.sort(this.resolvers);
	}

	public static CompositeTokenResolver of(TokenResolver... resolvers) {
		return new CompositeTokenResolver(resolvers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Token> resolve(WebRequest request) {
		for (TokenResolver it : resolvers) {
			Optional<Token> op = doResolve(it, request);
			if (op.isPresent()) {
				return op;
			}
		}
		return Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<TokenResolver> iterator() {
		return this.resolvers.iterator();
	}

	private Optional<Token> doResolve(@Nullable TokenResolver resolver, WebRequest request) {
		try {
			if (resolver != null) {
				return resolver.resolve(request);
			} else {
				return Optional.empty();
			}
		} catch (Throwable ignored) {
			return Optional.empty();
		}
	}
}
