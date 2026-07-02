package com.github.yingzhuo.turbocharger.util;

import java.util.zip.CRC32;

public final class CRC32Utils {

	private CRC32Utils() {
		super();
	}

	public static long crc32Value(byte[] data) {
		var crc32 = new CRC32();
		crc32.update(data);
		return crc32.getValue();
	}

	public static String crc32Hex(byte[] data) {
		var value = crc32Value(data);
		return Long.toHexString(value);
	}

}
