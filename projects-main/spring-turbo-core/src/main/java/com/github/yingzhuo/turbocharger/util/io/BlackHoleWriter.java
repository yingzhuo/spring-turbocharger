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

import java.io.Writer;

/**
 * @author 应卓
 * @see #getInstance()
 * @since 2.0.0
 * @deprecated 使用 {@link Writer#nullWriter()} 代替
 */
@Deprecated(since = "3.3.1")
public final class BlackHoleWriter extends Writer {

	/**
	 * 私有构造方法
	 */
	private BlackHoleWriter() {
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static BlackHoleWriter getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public void write(char[] buf, int off, int len) {
		// nop
	}

	@Override
	public void flush() {
		// nop
	}

	@Override
	public void close() {
		// nop
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final BlackHoleWriter INSTANCE = new BlackHoleWriter();
	}

}
