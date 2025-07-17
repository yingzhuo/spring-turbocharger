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
package com.github.yingzhuo.turbocharger.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.lang.Nullable;

/**
 * 业务异常
 *
 * @author 应卓
 * @see BusinessAsserts
 * @since 3.3.1
 */
public final class BusinessException extends AbstractMessageResolvableException implements MessageSourceResolvable {

	/**
	 * 构造方法
	 *
	 * @param code      解析错误信息用code
	 * @param arguments 解析错误信息用参数
	 * @see MessageSourceResolvable
	 */
	public BusinessException(@Nullable String code, @Nullable Object... arguments) {
		super(code, arguments);
	}

	/**
	 * 构造方法
	 *
	 * @param defaultMessage 默认错误信息
	 * @see MessageSourceResolvable
	 */
	public BusinessException(@Nullable String defaultMessage) {
		super(defaultMessage);
	}

	/**
	 * 构造方法
	 *
	 * @param code           解析错误信息用code
	 * @param arguments      解析错误信息用参数
	 * @param defaultMessage 默认错误信息
	 * @see MessageSourceResolvable
	 */
	public BusinessException(@Nullable String code, @Nullable Object[] arguments, @Nullable String defaultMessage) {
		super(code, arguments, defaultMessage);
	}

}
