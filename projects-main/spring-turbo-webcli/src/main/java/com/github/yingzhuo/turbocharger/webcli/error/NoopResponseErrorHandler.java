package com.github.yingzhuo.turbocharger.webcli.error;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public final class NoopResponseErrorHandler implements ResponseErrorHandler {

	private NoopResponseErrorHandler() {
	}

	public static NoopResponseErrorHandler getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public boolean hasError(ClientHttpResponse response) {
		return false;
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final NoopResponseErrorHandler INSTANCE = new NoopResponseErrorHandler();
	}

}
