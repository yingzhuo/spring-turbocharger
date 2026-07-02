package com.github.yingzhuo.turbocharger.util;

import java.util.Base64;

public final class Base64Utils {

	private Base64Utils() {
		super();
	}

	public static byte[] encode(byte[] data) {
		return encode(data, true, true);
	}

	public static byte[] encode(byte[] data, boolean withoutPadding) {
		return encode(data, withoutPadding, true);
	}

	public static byte[] encode(byte[] data, boolean withoutPadding, boolean urlSafe) {
		Base64.Encoder encoder = urlSafe ? Base64.getUrlEncoder() : Base64.getEncoder();
		if (withoutPadding) {
			encoder = encoder.withoutPadding();
		}
		return encoder.encode(data);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static byte[] decode(byte[] data) {
		return decode(data, true);
	}

	public static byte[] decode(byte[] data, boolean urlSafe) {
		var decoder = urlSafe ? Base64.getUrlDecoder() : Base64.getDecoder();
		return decoder.decode(data);
	}

}
