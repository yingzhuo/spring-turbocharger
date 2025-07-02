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
package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.util.io.CloseUtils;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link Resource}等相关工具
 *
 * @author 应卓
 * @see ResourceLocationUtils
 * @since 3.3.2
 */
@SuppressWarnings("unchecked")
public final class ResourceUtils {

	private static final ResourceLoader RESOURCE_LOADER
		= ApplicationResourceLoader.get(ClassUtils.getDefaultClassLoader());

	private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER
		= ResourcePatternUtils.getResourcePatternResolver(RESOURCE_LOADER);

	/**
	 * 私有构造方法
	 */
	private ResourceUtils() {
		super();
	}

	/**
	 * 获取{@link ResourceLoader}实例
	 *
	 * @return {@link ResourceLoader}实例
	 * @see #getResourcePatternResolver()
	 */
	public static ResourceLoader getResourceLoader() {
		return RESOURCE_LOADER;
	}

	/**
	 * 获取{@link ResourcePatternResolver}实例
	 *
	 * @return {@link ResourcePatternResolver}实例
	 * @see #getResourceLoader()
	 */
	public static ResourcePatternResolver getResourcePatternResolver() {
		return RESOURCE_PATTERN_RESOLVER;
	}

	/**
	 * 加载资源
	 *
	 * @param location 资源位置
	 * @return 资源
	 */
	public static <T extends Resource> T loadResource(String location) {
		Assert.hasText(location, "location is required");
		return (T) RESOURCE_LOADER.getResource(location);
	}

	/**
	 * 加载资源并打开输入流
	 *
	 * @param location 资源位置
	 * @return inputStream
	 * @throws UncheckedIOException I/O错误
	 */
	public static InputStream loadResourceAsInputStream(String location) throws IOException {
		return loadResourceAsInputStream(location, -1);
	}

	/**
	 * 加载资源并打开输入流
	 *
	 * @param location   资源位置
	 * @param bufferSize bufferSize
	 * @return inputStream
	 * @throws UncheckedIOException I/O错误
	 */
	public static InputStream loadResourceAsInputStream(String location, int bufferSize) throws IOException {
		try {
			var in = loadResource(location).getInputStream();
			return bufferSize > 0 ? new BufferedInputStream(in, bufferSize) : in;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 通过某种模式，加载多个资源
	 *
	 * @param locationPattern 资源为止模式
	 * @return 结果
	 * @throws UncheckedIOException I/O错误
	 */
	public static List<Resource> loadResources(String locationPattern) {
		try {
			return Arrays.asList(RESOURCE_PATTERN_RESOLVER.getResources(locationPattern));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 读取资源中的文本
	 *
	 * @param location 资源位置
	 * @return 文本
	 * @throws UncheckedIOException I/O错误
	 */
	public static String readResourceAsString(String location) {
		return readResourceAsString(location, (Charset) null);
	}

	/**
	 * 读取资源中的文本
	 *
	 * @param location 资源位置
	 * @param charset  字符集
	 * @return 文本
	 * @throws UncheckedIOException I/O错误
	 */
	public static String readResourceAsString(String location, @Nullable Charset charset) {
		try {
			charset = Objects.requireNonNullElse(charset, StandardCharsets.UTF_8);
			return loadResource(location).getContentAsString(charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 读取资源中的文本
	 *
	 * @param location 资源位置
	 * @param charset  字符集
	 * @return 文本
	 * @throws UncheckedIOException I/O错误
	 */
	public static String readResourceAsString(String location, @Nullable String charset) {
		var cs = Optional.ofNullable(charset)
			.map(Charset::forName)
			.orElse(StandardCharsets.UTF_8);

		return readResourceAsString(location, cs);
	}

	/**
	 * 读取资源中的每一行
	 *
	 * @param location 资源位置
	 * @return 行
	 * @throws UncheckedIOException I/O错误
	 */
	public static Stream<String> readResourceAsLines(String location) {
		return readResourceAsString(location).lines();
	}

	/**
	 * 读取资源中的每一行
	 *
	 * @param location 资源位置
	 * @param charset  字符集
	 * @return 行
	 * @throws UncheckedIOException I/O错误
	 */
	public static Stream<String> readResourceAsLines(String location, @Nullable Charset charset) {
		return readResourceAsString(location, charset).lines();
	}

	/**
	 * 读取资源中的每一行
	 *
	 * @param location 资源位置
	 * @param charset  字符集
	 * @return 行
	 * @throws UncheckedIOException I/O错误
	 */
	public static Stream<String> readResourceAsLines(String location, @Nullable String charset) {
		return readResourceAsString(location, charset).lines();
	}

	/**
	 * 加载Properties文件
	 *
	 * @param location  资源位置
	 * @param xmlFormat 是否为xml格式
	 * @return 加载的实例
	 * @throws UncheckedIOException I/O错误
	 */
	public static Properties loadResourceAsProperties(String location, boolean xmlFormat) {
		Assert.notNull(location, "resource location must not be null");
		try (var inputStream = loadResourceAsInputStream(location)) {
			var properties = new Properties();
			if (xmlFormat) {
				properties.loadFromXML(inputStream);
			} else {
				properties.load(inputStream);
			}
			return properties;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 加载Properties文件并转换成Map类型
	 *
	 * @param location  资源位置
	 * @param xmlFormat 是否为xml格式
	 * @return 加载的实例
	 * @throws UncheckedIOException I/O错误
	 */
	public static Map<String, Object> loadResourceAsPropertiesConventToMap(String location, boolean xmlFormat) {
		return loadResourceAsProperties(location, xmlFormat)
			.entrySet()
			.stream()
			.filter(entry -> entry.getKey() instanceof String)
			.collect(Collectors.toMap(
				e -> e.getKey().toString(),
				e -> e.getValue().toString()
			));
	}

	/**
	 * 读取资源中的二进制数据
	 *
	 * @param location 资源位置
	 * @return 二进制数据
	 * @throws UncheckedIOException I/O错误
	 */
	public static byte[] readResourceAsBytes(String location) {
		try {
			var resource = loadResource(location);
			return resource.getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 尝试关闭资源
	 *
	 * @param resource 资源
	 */
	public static void close(@Nullable Resource resource) {
		if (resource == null) {
			return;
		}

		if (resource.isOpen()) {
			try {
				CloseUtils.closeQuietly(resource.getInputStream());
			} catch (IOException ignored) {
				// noop
			}
		}

		if (resource instanceof WritableResource wr) {
			try {
				CloseUtils.closeQuietly(wr.getOutputStream());
			} catch (IOException ignored) {
				// noop
			}
		}
	}

}
