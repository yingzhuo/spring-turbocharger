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

import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.github.yingzhuo.turbocharger.util.io.CloseUtils.closeQuietly;
import static java.nio.charset.StandardCharsets.UTF_8;

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
	public static InputStream loadResourceAsInputStream(String location) {
		try {
			return loadResource(location).getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 尝试多个资源位置，直到找到一个存在的资源为止
	 *
	 * @param locations 资源位置
	 * @return 结果或{@code null}
	 */
	@Nullable
	public static Resource loadFirstExistsResource(@Nullable Iterable<String> locations) {
		if (locations == null) {
			return null;
		}

		for (var location : locations) {
			if (!StringUtils.hasText(location)) {
				continue;
			}

			try {
				var resource = RESOURCE_LOADER.getResource(location);
				if (resource.exists()) {
					return resource;
				}
			} catch (Exception ignored) {
			}
		}
		return null;
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
		return readResourceAsString(location, null);
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
			charset = Objects.requireNonNullElse(charset, UTF_8);
			return loadResource(location).getContentAsString(charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 加载Properties文件
	 *
	 * @param location 资源位置
	 * @return 加载的实例
	 * @throws UncheckedIOException I/O错误
	 */
	public static Properties loadResourceAsProperties(String location) {
		return loadResourceAsProperties(location, false);
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
				closeQuietly(resource.getInputStream());
			} catch (IOException ignored) {
				// noop
			}
		}

		if (resource instanceof WritableResource wr) {
			try {
				closeQuietly(wr.getOutputStream());
			} catch (IOException ignored) {
				// noop
			}
		}
	}

}
