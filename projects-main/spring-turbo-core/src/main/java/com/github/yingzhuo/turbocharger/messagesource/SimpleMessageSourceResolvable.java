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
