/*
 *
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.yingzhuo.turbocharger.util.io;

import com.github.yingzhuo.turbocharger.util.StringFormatter;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * {@link Path}相关工具
 *
 * @author 应卓
 * @see PathUtils
 * @see Path
 * @since 1.0.12
 */
public final class PathTreeUtils {

	/**
	 * 私有构造方法
	 */
	private PathTreeUtils() {
	}

	/**
	 * 列出目录下所有子目录或文件
	 *
	 * @param path 指定目录或文件
	 * @return 所有子目录和文件
	 */
	public static Stream<Path> list(Path path) {
		return list(path, Integer.MAX_VALUE);
	}

	/**
	 * 列出目录下所有子目录或文件
	 *
	 * @param path     指定目录或文件
	 * @param maxDepth 下钻目录层数 (从0开始)
	 * @return 所有子目录和文件
	 */
	public static Stream<Path> list(Path path, int maxDepth) {
		Assert.notNull(path, "path is required");
		Assert.isTrue(maxDepth >= 0, "maxDepth must greater than 0");

		if (!PathUtils.isExists(path)) {
			final String msg = StringFormatter.format("'{}' not exists", path);
			throw IOExceptionUtils.toUnchecked(msg);
		}

		try {
			return Files.walk(path, maxDepth, FileVisitOption.FOLLOW_LINKS);
		} catch (IOException e) {
			throw IOExceptionUtils.toUnchecked(e);
		}
	}

}
