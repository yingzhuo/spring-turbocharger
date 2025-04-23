/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.util.io;

import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * {@link IOException}相关工具
 *
 * @author 应卓
 * @since 1.0.10
 */
public final class IOExceptionUtils {

	/**
	 * 私有构造方法
	 */
	private IOExceptionUtils() {
		super();
	}

	/**
	 * 转换成未检查异常
	 *
	 * @param e IOException实例
	 * @return 未检查异常
	 */
	public static UncheckedIOException toUnchecked(IOException e) {
		return new UncheckedIOException(e.getMessage(), e);
	}

	/**
	 * 转换成未检查异常
	 *
	 * @param message 消息文本
	 * @return 未检查异常
	 */
	public static UncheckedIOException toUnchecked(@Nullable String message) {
		return toUnchecked(new IOException(message));
	}

}
