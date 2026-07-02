package com.github.yingzhuo.turbocharger.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public final class FileUtils {

	private FileUtils() {
	}

	public static Path toPath(File file) {
		return file.toPath();
	}

	public static File createFile(String first, String... more) {
		return PathUtils.toFile(PathUtils.createPath(first, more));
	}

	public static void createFile(File file) {
		PathUtils.createFile(file.toPath());
	}

	public static void createDirectory(File file) {
		PathUtils.createDirectory(file.toPath());
	}

	public static void move(File source, File target, boolean replaceExisting) {
		PathUtils.move(source.toPath(), target.toPath(), replaceExisting);
	}

	public static void copy(File source, File target, boolean replaceExisting) {
		PathUtils.copy(source.toPath(), target.toPath(), replaceExisting);
	}

	public static void touch(File file) {
		PathUtils.touch(file.toPath());
	}

	public static boolean isExists(File file) {
		return Files.exists(file.toPath());
	}

	public static boolean isDirectory(File file) {
		return Files.isDirectory(file.toPath());
	}

	public static boolean isEmptyDirectory(File file) {
		return PathUtils.isEmptyDirectory(file.toPath());
	}

	public static boolean isRegularFile(File file) {
		return Files.isRegularFile(file.toPath());
	}

	public static boolean isSymbolicLink(File file) {
		return Files.isSymbolicLink(file.toPath());
	}

	public static boolean isHidden(File file) {
		try {
			return Files.isHidden(file.toPath());
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean isReadableAndWritable(File file) {
		return PathUtils.isReadableAndWritable(file.toPath());
	}

	public static boolean isReadable(File file) {
		return PathUtils.isReadable(file.toPath());
	}

	public static boolean isWritable(File file) {
		return PathUtils.isWritable(file.toPath());
	}

	public static boolean isExecutable(File file) {
		return PathUtils.isExecutable(file.toPath());
	}

	public static long size(File file) {
		return PathUtils.size(file.toPath());
	}

	public static void delete(File file) {
		PathUtils.delete(file.toPath());
	}

	public static void deleteQuietly(File file) {
		PathUtils.deleteQuietly(file.toPath());
	}

	public static void cleanDirectory(File file) {
		PathUtils.cleanDirectory(file.toPath());
	}

	public static void cleanDirectoryQuietly(File file) {
		PathUtils.cleanDirectoryQuietly(file.toPath());
	}

	public static Date getCreationTime(File file) {
		return PathUtils.getCreationTime(file.toPath());
	}

	public static Date getLastModifiedTime(File file) {
		return PathUtils.getLastModifiedTime(file.toPath());
	}

	public static Date getLastAccessTime(File file) {
		return PathUtils.getLastAccessTime(file.toPath());
	}

	public static List<String> readLines(File file) {
		return PathUtils.readLines(file.toPath());
	}

	public static List<String> readLines(File file, Charset charset) {
		return PathUtils.readLines(file.toPath(), charset);
	}

	public static byte[] readBytes(File file) {
		return PathUtils.readBytes(file.toPath());
	}

	public static void writeBytes(File file, byte[] bytes, boolean createIfNotExists, boolean append) {
		PathUtils.writeBytes(file.toPath(), bytes, createIfNotExists, append);
	}

	public static void writeLines(File file, List<String> lines, boolean createIfNotExists, boolean append) {
		PathUtils.writeLines(file.toPath(), lines, createIfNotExists, append);
	}

	public static void writeLines(File file, List<String> lines, Charset charset, boolean createIfNotExists,
								  boolean append) {
		PathUtils.writeLines(file.toPath(), lines, charset, createIfNotExists, append);
	}

	public static boolean isSameFile(File f1, File f2) {
		return PathUtils.isSameFile(f1.toPath(), f2.toPath());
	}

}
