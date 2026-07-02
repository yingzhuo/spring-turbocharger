package com.github.yingzhuo.turbocharger.databinding;

import org.springframework.validation.MessageCodesResolver;

public final class DirectMessageCodesResolver implements MessageCodesResolver {

	private DirectMessageCodesResolver() {
		super();
	}

	public static DirectMessageCodesResolver getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName) {
		return new String[]{errorCode};
	}

	@Override
	public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
		return new String[]{errorCode};
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final DirectMessageCodesResolver INSTANCE = new DirectMessageCodesResolver();
	}

}
