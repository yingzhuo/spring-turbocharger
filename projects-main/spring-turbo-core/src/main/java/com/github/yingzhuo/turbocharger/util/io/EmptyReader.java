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

import java.io.Reader;

/**
 * 空的 {@link Reader}
 *
 * @author 应卓
 * @see #getInstance()
 * @since 1.0.8
 * @deprecated 使用 {@link Reader#nullReader()} 替代
 */
@Deprecated(since = "3.4.0")
public final class EmptyReader extends Reader {

	/**
	 * 私有构造方法
	 */
	private EmptyReader() {
	}

	public static EmptyReader getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public int read(char[] buf, int off, int len) {
		return -1;
	}

	@Override
	public void close() {
		// noop
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final EmptyReader INSTANCE = new EmptyReader();
	}

}
