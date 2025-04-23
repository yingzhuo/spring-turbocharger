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
package com.github.yingzhuo.turbocharger.messagesource;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * {@link MessageSourceResolvable} 简单实现
 *
 * @param defaultMessage 默认错误信息
 * @author 应卓
 * @see MessageSourceResolvable#getDefaultMessage()
 * @since 3.3.1
 */
public record StringMessageSourceResolvable(
	String defaultMessage) implements MessageSourceResolvable, Serializable {

	/**
	 * 构造方法
	 *
	 * @param defaultMessage 默认错误文本
	 */
	public StringMessageSourceResolvable {
		Assert.hasText(defaultMessage, "defaultMessage is required");
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public String[] getCodes() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Object[] getArguments() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@NonNull
	@Override
	public String getDefaultMessage() {
		return defaultMessage;
	}

}
