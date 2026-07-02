package com.github.yingzhuo.turbocharger.util.io;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class PathTreeUtils {

	private PathTreeUtils() {
	}

	public static Stream<Path> list(Path path) {
		return list(path, Integer.MAX_VALUE);
	}

	public static Stream<Path> list(Path path, int maxDepth) {
		Assert.notNull(path, "path is required");
		Assert.isTrue(maxDepth >= 0, "maxDepth must greater than 0");

		if (!PathUtils.isExists(path)) {
			final String msg = StringFormatter.format("'{}' not exists", path);
			throw new UncheckedIOException(new IOException(msg));
		}

		try {
			return Files.walk(path, maxDepth, FileVisitOption.FOLLOW_LINKS);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
