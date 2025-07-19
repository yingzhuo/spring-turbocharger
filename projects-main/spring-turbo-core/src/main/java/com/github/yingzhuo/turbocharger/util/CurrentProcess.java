/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util;

/**
 * 获取当前进程的相关信息
 *
 * @author 应卓
 * @since 3.1.1
 */
public final class CurrentProcess {

	/**
	 * 私有构造方法
	 */
	private CurrentProcess() {
	}

	/**
	 * pid
	 *
	 * @return pid
	 */
	public static long pid() {
		return SyncAvoid.PROCESS_HANDLE.pid();
	}

	/**
	 * 父进程PID
	 *
	 * @return 父进程PID或-1
	 */
	public static long parentPid() {
		return SyncAvoid.PROCESS_HANDLE.parent()
			.map(ProcessHandle::pid)
			.orElse(-1L);
	}

	/**
	 * 当前进程所属用户
	 *
	 * @return 进程所属用户名，无法确定时返回空字符串
	 */
	public static String user() {
		return SyncAvoid.PROCESS_HANDLE
			.info()
			.user()
			.orElse(StringPool.EMPTY);
	}

	// 延迟加载
	private static class SyncAvoid {
		private static final ProcessHandle PROCESS_HANDLE = ProcessHandle.current();
	}

}
