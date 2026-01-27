/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.messagesource;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;

import java.io.Serializable;

/**
 * {@link MessageSourceResolvable} 简单实现
 *
 * @author 应卓
 * @since 3.3.1
 */
public final class SimpleMessageSourceResolvable implements MessageSourceResolvable, Serializable {

	@Nullable
	private final String code;

	@Nullable
	private final Object[] arguments;

	@Nullable
	private final String defaultMessages;

	public SimpleMessageSourceResolvable(@Nullable String code, @Nullable Object[] arguments, @Nullable String defaultMessages) {
		this.code = StringUtils.blankToNull(code);
		this.arguments = ArrayUtils.emptyToNull(arguments);
		this.defaultMessages = StringUtils.blankToNull(defaultMessages);
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public String[] getCodes() {
		return code != null ? new String[]{code} : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public String getDefaultMessage() {
		return defaultMessages;
	}

}
