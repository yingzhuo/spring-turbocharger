package com.github.yingzhuo.turbocharger.util;

import java.util.HexFormat;

public final class HexUtils {

	private HexUtils() {
		super();
	}

	public static String encodeToString(byte[] bytes) {
		var format = HexFormat.of();
		return format.formatHex(bytes);
	}

	public static byte[] decodeToBytes(String hexString) {
		var format = HexFormat.of();
		return format.parseHex(hexString);
	}

}
