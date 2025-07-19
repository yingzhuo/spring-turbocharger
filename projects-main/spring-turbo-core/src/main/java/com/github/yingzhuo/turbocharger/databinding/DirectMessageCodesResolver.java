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
package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.validation.MessageCodesResolver;

/**
 * 简易{@link MessageCodesResolver} 实现。
 *
 * @author 应卓
 * @see #getInstance()
 * @since 3.3.1
 */
public final class DirectMessageCodesResolver implements MessageCodesResolver {

	/**
	 * 私有构造方法
	 */
	private DirectMessageCodesResolver() {
		super();
	}

	/**
	 * 获取本类型单例
	 *
	 * @return 单例实例
	 */
	public static DirectMessageCodesResolver getInstance() {
		return SyncAvoid.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName) {
		return new String[]{errorCode};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
		return new String[]{errorCode};
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final DirectMessageCodesResolver INSTANCE = new DirectMessageCodesResolver();
	}

}
