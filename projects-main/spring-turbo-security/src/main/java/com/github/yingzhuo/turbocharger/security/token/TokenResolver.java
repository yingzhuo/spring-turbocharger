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

import org.springframework.core.Ordered;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

/**
 * 令牌解析器 从{@code HTTP}请求中获取令牌
 *
 * @author 应卓
 * @see #builder()
 * @see Token
 * @see TokenResolverBuilder
 * @see CompositeTokenResolver
 * @since 1.0.0
 */
@FunctionalInterface
public interface TokenResolver extends Ordered {

	/**
	 * 新建创建器
	 *
	 * @return 创建器
	 */
	public static TokenResolverBuilder builder() {
		return new TokenResolverBuilder();
	}

	/**
	 * 解析令牌
	 *
	 * @param request HTTP请求
	 * @return 令牌Optional，不能成功解析时返回empty-optional
	 */
	public Optional<Token> resolve(WebRequest request);

	/**
	 * 获取排序值
	 * <p>
	 * 多个令牌解析器同时作用时，可自由指定顺序。排序值越大，排序越靠后。
	 *
	 * @return 排序值
	 * @see CompositeTokenResolver
	 * @see Ordered#getOrder()
	 * @see Ordered#LOWEST_PRECEDENCE
	 * @see Ordered#HIGHEST_PRECEDENCE
	 * @see org.springframework.core.OrderComparator
	 */
	@Override
	public default int getOrder() {
		return 0;
	}

}
