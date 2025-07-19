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
package com.github.yingzhuo.turbocharger.exception;

import com.github.yingzhuo.turbocharger.core.LocaleUtils;
import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.messagesource.StringMessageSourceResolvable;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * 实现了 {@link MessageSourceResolvable} 的异常类
 *
 * @author 应卓
 * @see MessageSource
 * @see MessageSourceResolvable
 * @see Locale
 * @see org.springframework.context.i18n.LocaleContextHolder
 * @since 3.3.1
 */
public abstract class AbstractMessageResolvableException extends IllegalArgumentException implements MessageSourceResolvable {

	@Nullable
	private final String code;

	@Nullable
	private final Object[] arguments;

	@Nullable
	private final String defaultMessage;

	/**
	 * 构造方法
	 *
	 * @param code      解析错误信息用code
	 * @param arguments 解析错误信息用参数
	 * @see MessageSourceResolvable
	 */
	public AbstractMessageResolvableException(@Nullable String code, @Nullable Object[] arguments) {
		this(code, arguments, null);
	}

	/**
	 * 构造方法
	 *
	 * @param defaultMessage 默认错误信息
	 * @see MessageSourceResolvable
	 */
	public AbstractMessageResolvableException(@Nullable String defaultMessage) {
		this(null, null, defaultMessage);
	}

	/**
	 * 构造方法
	 *
	 * @param code           解析错误信息用code
	 * @param arguments      解析错误信息用参数
	 * @param defaultMessage 默认错误信息
	 * @see MessageSourceResolvable
	 */
	public AbstractMessageResolvableException(@Nullable String code, @Nullable Object[] arguments, @Nullable String defaultMessage) {
		super(StringUtils.blankToNull(defaultMessage));
		this.code = StringUtils.blankToNull(code);
		this.arguments = ArrayUtils.emptyToNull(arguments);
		this.defaultMessage = StringUtils.blankToNull(defaultMessage);
	}

	// 内部用工具
	static String getMessage(@Nullable MessageSource messageSource, MessageSourceResolvable messageSourceResolvable) {
		return getMessage(messageSource, messageSourceResolvable, null);
	}

	// 内部用工具
	static String getMessage(@Nullable MessageSource messageSource, MessageSourceResolvable messageSourceResolvable, @Nullable Locale locale) {
		Assert.notNull(messageSourceResolvable, "messageSourceResolvable is required");

		if (messageSourceResolvable instanceof StringMessageSourceResolvable stringMessageSourceResolvable) {
			return stringMessageSourceResolvable.getDefaultMessage();
		}

		messageSource = messageSource != null ? messageSource : SpringUtils.getMessageSource();
		locale = locale != null ? locale : LocaleUtils.getLocale(true);

		try {
			return messageSource.getMessage(messageSourceResolvable, locale);
		} catch (NoSuchMessageException e) {
			var defaultMsg = messageSourceResolvable.getDefaultMessage();
			if (defaultMsg != null) {
				return defaultMsg;
			}
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final String[] getCodes() {
		return code != null ? new String[]{code} : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final Object[] getArguments() {
		return this.arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public final String getDefaultMessage() {
		return this.defaultMessage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		try {
			return toString((MessageSource) null, null);
		} catch (Throwable e) {
			return super.toString();
		}
	}

	/**
	 * 从{@link MessageSource} 解析出错误信息
	 *
	 * @param messageSource 信息源
	 * @return 错误信息
	 */
	public final String toString(@Nullable MessageSource messageSource) {
		return getMessage(messageSource, this);
	}

	/**
	 * 从{@link MessageSource} 解析出错误信息
	 *
	 * @param messageSource 信息源
	 * @param locale        locale
	 * @return 错误信息
	 */
	public final String toString(@Nullable MessageSource messageSource, @Nullable Locale locale) {
		return getMessage(messageSource, this, locale);
	}

}
