/*
 * Copyright 2025-present the original author or authors.
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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

/**
 * {@link Socket}相关工具
 *
 * @author 应卓
 * @since 1.0.0
 */
public final class SocketUtils {

	/**
	 * 私有构造方法
	 */
	private SocketUtils() {
	}

	/**
	 * 通过TCP判断远程socket是否可以联通
	 *
	 * @param address         地址
	 * @param port            端口号
	 * @param timeoutInMillis timeout毫秒数
	 * @return 能联通时返回 {@code true} 否则返回 {@code false}
	 */
	public static boolean isReachable(String address, int port, int timeoutInMillis) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(address, port), timeoutInMillis);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * 通过TCP判断远程socket是否可以联通
	 *
	 * @param address 地址
	 * @param port    端口号
	 * @param timeout timeout
	 * @return 能联通时返回 {@code true} 否则返回 {@code false}
	 */
	public static boolean isReachable(String address, int port, Duration timeout) {
		return isReachable(address, port, (int) timeout.toMillis());
	}

}
