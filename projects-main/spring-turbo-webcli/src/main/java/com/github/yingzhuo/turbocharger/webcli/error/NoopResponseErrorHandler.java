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
package com.github.yingzhuo.turbocharger.webcli.error;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * @author 应卓
 * @see #getInstance()
 * @see org.springframework.web.client.NoOpResponseErrorHandler
 * @see org.springframework.web.client.DefaultResponseErrorHandler
 * @since 3.3.1
 */
public final class NoopResponseErrorHandler implements ResponseErrorHandler {

	/**
	 * 私有构造方法
	 */
	private NoopResponseErrorHandler() {
	}

	/**
	 * 获取 {@link NoopResponseErrorHandler} 实例
	 *
	 * @return {@link NoopResponseErrorHandler} 实例
	 */
	public static NoopResponseErrorHandler getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public boolean hasError(ClientHttpResponse response) {
		return false;
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final NoopResponseErrorHandler INSTANCE = new NoopResponseErrorHandler();
	}

}
