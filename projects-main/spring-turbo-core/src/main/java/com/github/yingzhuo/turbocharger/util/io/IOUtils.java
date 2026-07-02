package com.github.yingzhuo.turbocharger.util.io;

import org.jspecify.annotations.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class IOUtils {

	private IOUtils() {
		super();
	}

	public static int copy(InputStream in, OutputStream out) {
		try {
			return FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static int copy(Reader in, Writer out) {
		try {
			return FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void copy(byte[] in, OutputStream out) {
		try {
			StreamUtils.copy(in, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void copy(String in, OutputStream out) {
		copy(in, null, out);
	}

	public static void copy(String in, @Nullable Charset charset, OutputStream out) {
		charset = Objects.requireNonNullElse(charset, StandardCharsets.UTF_8);

		try {
			StreamUtils.copy(in, charset, out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static byte[] copyToByteArray(InputStream in) {
		try {
			return StreamUtils.copyToByteArray(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String copyToString(InputStream in) {
		return copyToString(in, null);
	}

	public static String copyToString(InputStream in, @Nullable Charset charset) {
		charset = Objects.requireNonNullElse(charset, StandardCharsets.UTF_8);

		try {
			return StreamUtils.copyToString(in, charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static long copyRange(InputStream in, OutputStream out, long start, long end) {
		try {
			return StreamUtils.copyRange(in, out, start, end);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static int drain(InputStream in) {
		try {
			return StreamUtils.drain(in);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
