package com.github.yingzhuo.turbocharger.util.io;

import org.jspecify.annotations.Nullable;

import java.nio.file.Paths;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

public final class FilenameUtils {

	private static final char UNIX_SEPARATOR = '/';
	private static final char WINDOWS_SEPARATOR = '\\';
	private static final char EXTENSION_SEPARATOR = '.';
	private static final int NOT_FOUND = -1;

	private FilenameUtils() {
		super();
	}

	public static String normalize(String filename) {
		return contact(filename);
	}

	public static String contact(String path, @Nullable String... more) {
		if (more != null)
			return Paths.get(path, more).normalize().toString();
		else {
			return Paths.get(path).normalize().toString();
		}
	}

	public static String getName(final String fileName) {
		requireNonNullChars(fileName);
		final int index = indexOfLastSeparator(fileName);
		return fileName.substring(index + 1);
	}

	public static String getBaseName(final String fileName) {
		return removeExtension(getName(fileName));
	}

	public static String getExtension(final String fileName) {
		final int index = indexOfExtension(fileName);
		if (index == NOT_FOUND) {
			return EMPTY;
		}
		return fileName.substring(index + 1);
	}

	public static String removeExtension(final String fileName) {
		requireNonNullChars(fileName);

		final int index = indexOfExtension(fileName);
		if (index == NOT_FOUND) {
			return fileName;
		}
		return fileName.substring(0, index);
	}

	public static int indexOfExtension(@Nullable final String fileName) {
		if (fileName == null) {
			return NOT_FOUND;
		}

		final int extensionPos = fileName.lastIndexOf(EXTENSION_SEPARATOR);
		final int lastSeparator = indexOfLastSeparator(fileName);
		return lastSeparator > extensionPos ? -1 : extensionPos;
	}

	private static int indexOfLastSeparator(@Nullable final String fileName) {
		if (fileName == null) {
			return NOT_FOUND;
		}
		final int lastUnixPos = fileName.lastIndexOf(UNIX_SEPARATOR);
		final int lastWindowsPos = fileName.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}

	private static void requireNonNullChars(final String path) {
		if (path.indexOf(0) >= 0) {
			throw new IllegalArgumentException("Null byte present in file/path name. There are no "
				+ "known legitimate use cases for such data, but several injection attacks may use it");
		}
	}

}
