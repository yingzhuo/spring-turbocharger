package com.github.yingzhuo.turbocharger.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class UTF8Utils {

	private UTF8Utils() {
		super();
	}

	public static byte[] encode(CharSequence string) {
		try {
			ByteBuffer bytes = UTF_8.newEncoder().encode(CharBuffer.wrap(string));
			byte[] bytesCopy = new byte[bytes.limit()];
			System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
			return bytesCopy;
		} catch (CharacterCodingException ex) {
			throw new IllegalArgumentException("Encoding failed", ex);
		}
	}

	public static String decode(byte[] bytes) {
		try {
			return UTF_8.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
		} catch (CharacterCodingException ex) {
			throw new IllegalArgumentException("Decoding failed", ex);
		}
	}

}
