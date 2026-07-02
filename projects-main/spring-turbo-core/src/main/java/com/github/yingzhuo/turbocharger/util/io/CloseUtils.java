package com.github.yingzhuo.turbocharger.util.io;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public final class CloseUtils {

	private CloseUtils() {
		super();
	}

	public static void closeQuietly(@Nullable Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {
				// noop
			}
		}
	}

	public static void closeQuietly(@Nullable AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignored) {
				// noop
			}
		}
	}

	public static void closeQuietly(@Nullable Resource resource) {
		if (resource != null) {
			try {
				closeQuietly(resource.getInputStream());
			} catch (IOException ignored) {
				// noop
			}
		}
	}

	public static void closeQuietly(@Nullable ExecutorService executorService) {
		if (executorService != null) {
			try {
				executorService.shutdown();
			} catch (Exception ignored) {
				// noop
			}
		}
	}

}
