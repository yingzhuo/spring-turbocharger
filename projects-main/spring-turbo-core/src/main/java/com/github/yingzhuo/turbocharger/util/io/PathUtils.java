package com.github.yingzhuo.turbocharger.util.io;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import com.github.yingzhuo.turbocharger.util.collection.ListFactories;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class PathUtils {

	private PathUtils() {
	}

	public static File toFile(Path path) {
		return path.toFile();
	}

	public static Path createPath(String first, String... more) {
		return Paths.get(first, more).normalize();
	}

	public static Path createAbsolutePath(String first, String... more) {
		return createPath(first, more).toAbsolutePath();
	}

	public static Path createFile(String first, String... more) {
		final Path path = createPath(first, more);
		createFile(path);
		return path;
	}

	public static void createFile(Path path) {
		try {
			boolean success = toFile(path).createNewFile();
			if (!success) {
				final String msg = StringFormatter.format("unable to create file: {}", path);
				throw new UncheckedIOException(new IOException(msg));
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Path createDirectory(String first, String... more) {
		final Path path = createPath(first, more);
		createDirectory(path);
		return path;
	}

	public static void createDirectory(Path path) {
		if (isExists(path)) {
			if (isDirectory(path)) {
				return;
			} else {
				final String msg = StringFormatter.format("unable to create dir: {}", path);
				throw new UncheckedIOException(new IOException(msg));
			}
		}

		boolean success = toFile(path).mkdirs();
		if (!success) {
			final String msg = StringFormatter.format("unable to create dir: {}", path);
			throw new UncheckedIOException(new IOException(msg));
		}
	}

	public static void move(Path source, Path target, boolean replaceExisting) {
		final List<CopyOption> copyOptions = ListFactories.newLinkedList();

		if (replaceExisting) {
			copyOptions.add(StandardCopyOption.REPLACE_EXISTING);
		}

		try {
			Files.move(source, target, copyOptions.toArray(new CopyOption[0]));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void copy(Path source, Path target, boolean replaceExisting) {
		final List<CopyOption> copyOptions = ListFactories.newLinkedList();

		if (replaceExisting) {
			copyOptions.add(StandardCopyOption.REPLACE_EXISTING);
		}

		try {
			Files.copy(source, target, copyOptions.toArray(new CopyOption[0]));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void touch(Path path) {
		try {
			if (Files.exists(path)) {
				Files.setLastModifiedTime(path, FileTime.from(Instant.now()));
			} else {
				Files.createFile(path);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static boolean isExists(Path path) {
		return Files.exists(path);
	}

	public static boolean isDirectory(Path path) {
		return Files.isDirectory(path);
	}

	public static boolean isEmptyDirectory(Path path) {
		if (!isDirectory(path)) {
			return false;
		}

		try {
			try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
				return !directory.iterator().hasNext();
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static boolean isRegularFile(Path path) {
		return Files.isRegularFile(path);
	}

	public static boolean isSymbolicLink(Path path) {
		return Files.isSymbolicLink(path);
	}

	public static boolean isHidden(Path path) {
		try {
			return Files.isHidden(path);
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean isReadableAndWritable(Path path) {
		return isReadable(path) && isWritable(path);
	}

	public static boolean isReadable(Path path) {
		return Files.isReadable(path);
	}

	public static boolean isWritable(Path path) {
		return Files.isWritable(path);
	}

	public static boolean isExecutable(Path path) {
		return Files.isExecutable(path);
	}

	public static long size(Path path) {
		if (!isExists(path)) {
			throw new UncheckedIOException(new IOException("file not exists"));
		}

		try {
			return Files.size(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void delete(Path path) {
		try {
			if (!isExists(path)) {
				return;
			}

			if (isRegularFile(path)) {
				Files.deleteIfExists(path);
			} else {
				FileSystemUtils.deleteRecursively(path);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void deleteQuietly(Path path) {
		try {
			delete(path);
		} catch (Throwable e) {
			// nop
		}
	}

	public static void cleanDirectory(Path path) {
		if (!isDirectory(path)) {
			return;
		}

		PathTreeUtils.list(path, 1).forEach(found -> {
			if (!found.equals(path)) {
				delete(found);
			}
		});
	}

	public static void cleanDirectoryQuietly(Path path) {
		if (!isDirectory(path)) {
			return;
		}

		PathTreeUtils.list(path, 1).forEach(found -> {
			if (!found.equals(path)) {
				deleteQuietly(found);
			}
		});
	}

	public static Date getCreationTime(Path path) {
		try {
			final BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
			return new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Date getLastModifiedTime(Path path) {
		try {
			final BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
			return new Date(attributes.lastModifiedTime().to(TimeUnit.MILLISECONDS));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Date getLastAccessTime(Path path) {
		try {
			final BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
			return new Date(attributes.lastAccessTime().to(TimeUnit.MILLISECONDS));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static List<String> readLines(Path path) {
		return readLines(path, UTF_8);
	}

	public static List<String> readLines(Path path, Charset charset) {
		try {
			return Files.readAllLines(path, charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static byte[] readBytes(Path path) {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void writeBytes(Path path, byte[] bytes, boolean createIfNotExists, boolean append) {
		final List<OpenOption> openOptions = ListFactories.newArrayList(StandardOpenOption.WRITE);

		if (createIfNotExists) {
			openOptions.add(StandardOpenOption.CREATE);
		}

		if (append) {
			openOptions.add(StandardOpenOption.APPEND);
		} else {
			openOptions.add(StandardOpenOption.TRUNCATE_EXISTING);
		}

		try {
			Files.write(path, bytes, openOptions.toArray(new OpenOption[0]));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void writeLines(Path path, List<String> lines, boolean createIfNotExists, boolean append) {
		writeLines(path, lines, UTF_8, createIfNotExists, append);
	}

	public static void writeLines(Path path, List<String> lines, Charset charset, boolean createIfNotExists,
								  boolean append) {
		final List<OpenOption> openOptions = ListFactories.newArrayList(StandardOpenOption.WRITE);

		if (createIfNotExists) {
			openOptions.add(StandardOpenOption.CREATE);
		}

		if (append) {
			openOptions.add(StandardOpenOption.APPEND);
		} else {
			openOptions.add(StandardOpenOption.TRUNCATE_EXISTING);
		}

		try {
			Files.write(path, lines, charset, openOptions.toArray(new OpenOption[0]));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static Path toAbsolutePath(Path path) {
		return path.toAbsolutePath();
	}

	public static boolean isSameFile(Path p1, Path p2) {
		try {
			return Files.isSameFile(p1, p2);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
