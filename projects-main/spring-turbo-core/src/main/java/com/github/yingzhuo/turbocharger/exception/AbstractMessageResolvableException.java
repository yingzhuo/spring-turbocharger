package com.github.yingzhuo.turbocharger.exception;

import com.github.yingzhuo.turbocharger.core.LocaleUtils;
import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.messagesource.StringMessageSourceResolvable;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.Assert;

import java.util.Locale;

public abstract class AbstractMessageResolvableException extends IllegalArgumentException implements MessageSourceResolvable {

	@Nullable
	private final String code;

	@Nullable
	private final Object[] arguments;

	@Nullable
	private final String defaultMessage;

	public AbstractMessageResolvableException(@Nullable String code, @Nullable Object[] arguments) {
		this(code, arguments, null);
	}

	public AbstractMessageResolvableException(@Nullable String defaultMessage) {
		this(null, null, defaultMessage);
	}

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

	@Nullable
	@Override
	public final String[] getCodes() {
		return code != null ? new String[]{code} : null;
	}

	@Nullable
	@Override
	public final Object[] getArguments() {
		return this.arguments;
	}

	@Nullable
	@Override
	public final String getDefaultMessage() {
		return this.defaultMessage;
	}

	@Override
	public String toString() {
		try {
			return toString((MessageSource) null, null);
		} catch (Throwable e) {
			return super.toString();
		}
	}

	public final String toString(@Nullable MessageSource messageSource) {
		return getMessage(messageSource, this);
	}

	public final String toString(@Nullable MessageSource messageSource, @Nullable Locale locale) {
		return getMessage(messageSource, this, locale);
	}

}
