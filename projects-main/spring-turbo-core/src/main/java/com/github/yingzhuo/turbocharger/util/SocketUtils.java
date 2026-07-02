package com.github.yingzhuo.turbocharger.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

public final class SocketUtils {

	private SocketUtils() {
	}

	public static boolean isReachable(String address, int port, int timeoutInMillis) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(address, port), timeoutInMillis);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public static boolean isReachable(String address, int port, Duration timeout) {
		return isReachable(address, port, (int) timeout.toMillis());
	}

}
