package com.github.yingzhuo.turbocharger.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;

public final class BusinessException extends AbstractMessageResolvableException implements MessageSourceResolvable {

	public BusinessException(@Nullable String code, @Nullable Object... arguments) {
		super(code, arguments);
	}

	public BusinessException(@Nullable String defaultMessage) {
		super(defaultMessage);
	}

	public BusinessException(@Nullable String code, @Nullable Object[] arguments, @Nullable String defaultMessage) {
		super(code, arguments, defaultMessage);
	}

}
