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
package com.github.yingzhuo.turbocharger.util.io;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * 本类封装资源关闭操作
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class CloseUtils {

	/**
	 * 私有构造方法
	 */
	private CloseUtils() {
		super();
	}

	/**
	 * 关闭资源
	 *
	 * @param closeable 资源
	 */
	public static void closeQuietly(@Nullable Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {
				// noop
			}
		}
	}

	/**
	 * 关闭资源
	 *
	 * @param closeable 资源
	 */
	public static void closeQuietly(@Nullable AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignored) {
				// noop
			}
		}
	}

	/**
	 * 关闭资源
	 *
	 * @param resource 资源
	 */
	public static void closeQuietly(@Nullable Resource resource) {
		if (resource != null) {
			try {
				closeQuietly(resource.getInputStream());
			} catch (IOException ignored) {
				// noop
			}
		}
	}

	/**
	 * 关闭资源
	 *
	 * @param executorService 线程池之类的资源
	 */
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
