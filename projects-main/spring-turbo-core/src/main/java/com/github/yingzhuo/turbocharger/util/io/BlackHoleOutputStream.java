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

import java.io.OutputStream;

/**
 * @author 应卓
 * @see #getInstance()
 * @since 1.0.8
 * @deprecated 使用 {@link OutputStream#nullOutputStream()} 代替
 */
@Deprecated(since = "3.3.1")
public final class BlackHoleOutputStream extends OutputStream {

	/**
	 * 私有构造方法
	 */
	private BlackHoleOutputStream() {
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static BlackHoleOutputStream getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public void write(int b) {
		// nop
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final BlackHoleOutputStream INSTANCE = new BlackHoleOutputStream();
	}

}
