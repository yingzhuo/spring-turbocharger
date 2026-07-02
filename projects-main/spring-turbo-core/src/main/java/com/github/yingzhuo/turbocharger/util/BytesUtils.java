package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.stream.Stream;

public final class BytesUtils {

	private BytesUtils() {
		super();
	}

	public static byte[] concat(@Nullable byte[]... bytesArray) {
		if (bytesArray == null || bytesArray.length == 0) {
			return new byte[0];
		}

		var count = Stream.of(bytesArray)
			.map(it -> it.length)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		Stream.of(bytesArray)
			.forEach(buffer::put);
		return buffer.array();
	}

	public static byte[] concat(@Nullable Collection<byte[]> bytesCollection) {
		if (bytesCollection == null || bytesCollection.isEmpty()) {
			return new byte[0];
		}

		var count = Stream.of(bytesCollection)
			.map(Collection::size)
			.reduce(0, Integer::sum);

		var combined = new byte[count];
		var buffer = ByteBuffer.wrap(combined);
		bytesCollection.forEach(buffer::put);
		return buffer.array();
	}

}
