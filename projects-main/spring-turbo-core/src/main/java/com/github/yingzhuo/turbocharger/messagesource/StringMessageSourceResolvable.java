package com.github.yingzhuo.turbocharger.messagesource;

import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.Assert;

import java.io.Serializable;

public record StringMessageSourceResolvable(
	String defaultMessage) implements MessageSourceResolvable, Serializable {

	public StringMessageSourceResolvable {
		Assert.hasText(defaultMessage, "defaultMessage is required");
	}

	@Nullable
	@Override
	public String[] getCodes() {
		return null;
	}

	@Nullable
	@Override
	public Object[] getArguments() {
		return null;
	}


	@Override
	public String getDefaultMessage() {
		return defaultMessage;
	}

}
