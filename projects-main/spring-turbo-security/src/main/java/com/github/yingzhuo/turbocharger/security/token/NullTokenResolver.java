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

import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * 一直返回empty-optional的令牌解析器实现
 *
 * @author 应卓
 * @see #getInstance()
 * @since 1.0.0
 */
public final class NullTokenResolver implements TokenResolver {

	/**
	 * 私有构造方法
	 */
	private NullTokenResolver() {
		super();
	}

	/**
	 * 获取本类单例
	 *
	 * @return 单例实例
	 */
	public static NullTokenResolver getInstance() {
		return SyncAvoid.INSTANCE;
	}

	/**
	 * 解析令牌
	 *
	 * @param request HTTP请求
	 * @return empty-optional
	 */
	@Override
	public Optional<Token> resolve(NativeWebRequest request) {
		return Optional.empty();
	}

	/**
	 * 排序参数
	 *
	 * @return 排序值 (最低)
	 */
	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	// -----------------------------------------------------------------------------------------------------------------

	// 延迟加载
	private static class SyncAvoid {
		private static final NullTokenResolver INSTANCE = new NullTokenResolver();
	}

}
