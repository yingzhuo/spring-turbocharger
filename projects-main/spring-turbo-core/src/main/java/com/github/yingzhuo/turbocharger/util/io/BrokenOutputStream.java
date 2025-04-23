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

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 应卓
 * @see #getInstance()
 * @since 1.1.1
 */
@Deprecated(since = "3.3.1")
public class BrokenOutputStream extends OutputStream {

	/**
	 * 私有构造方法
	 */
	private BrokenOutputStream() {
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static BrokenOutputStream getInstance() {
		return SyncAvoid.INSTANCE;
	}

	@Override
	public void write(int b) throws IOException {
		throw new IOException("broken");
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final BrokenOutputStream INSTANCE = new BrokenOutputStream();
	}

}
