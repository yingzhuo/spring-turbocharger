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

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNullElse;

/**
 * 有更丰富功能的 {@link Resource}。<br>
 * 显而易见，这是一个装饰器。
 *
 * @author 应卓
 * @see #of(String)
 * @see #of(Resource)
 * @since 3.3.2
 */
public interface RichResource extends Resource, Serializable {

	/**
	 * 装饰一个{@link Resource}
	 *
	 * @param resource 被装饰的对象
	 * @return 结果
	 */
	public static RichResource of(Resource resource) {
		return new RichResourceImpl(resource);
	}

	/**
	 * 从location加载一个{@link RichResource}
	 *
	 * @param location 资源位置
	 * @return 结果
	 */
	public static RichResource of(String location) {
		var res = ResourceUtils.loadResource(location);
		return new RichResourceImpl(res);
	}

	/**
	 * 判断代理的对象是否是一个文件或物理设备
	 *
	 * @return 判断结果
	 */
	public default boolean isPhysicalResource() {
		try {
			getFile();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 获取{@link Path}
	 *
	 * @return path
	 * @throws IOException I/O错误
	 */
	public default Path getPath() throws IOException {
		return getFile().toPath().toAbsolutePath();
	}

	/**
	 * 获取一个{@link Reader}
	 *
	 * @return reader
	 * @throws IOException I/O错误
	 */
	public default Reader getReader() throws IOException {
		return getReader(null);
	}

	/**
	 * 获取一个{@link Reader}
	 *
	 * @param charset 编码
	 * @return reader
	 * @throws IOException I/O错误
	 */
	public default Reader getReader(@Nullable Charset charset) throws IOException {
		return new InputStreamReader(getInputStream(), requireNonNullElse(charset, UTF_8));
	}

	/**
	 * 将资源转换成一个文本
	 *
	 * @return 文本
	 * @throws IOException I/O错误
	 */
	public default String getAsText() throws IOException {
		return getAsText(null);
	}

	/**
	 * 将资源转换成一个文本
	 *
	 * @param charset 编码
	 * @return 文本
	 * @throws IOException I/O错误
	 */
	public default String getAsText(@Nullable Charset charset) throws IOException {
		return getContentAsString(requireNonNullElse(charset, UTF_8));
	}

	/**
	 * 将资源文本逐行读取
	 *
	 * @return 逐行的文本
	 * @throws IOException I/O错误
	 */
	public default Stream<String> getLinesAsStream() throws IOException {
		return getLinesAsStream(null);
	}

	/**
	 * 将资源文本逐行读取
	 *
	 * @param charset 编码
	 * @return 逐行的文本
	 * @throws IOException I/O错误
	 */
	public default Stream<String> getLinesAsStream(@Nullable Charset charset) throws IOException {
		charset = requireNonNullElse(charset, UTF_8);
		return getAsText(charset).lines();
	}

	/**
	 * 判断资源是否是一个文件而非目录
	 *
	 * @return 判断结果
	 */
	public default boolean isRegularFile() {
		try {
			return PathUtils.isRegularFile(getPath());
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 判断资源是否是一个目录
	 *
	 * @return 判断结果
	 */
	public default boolean isDirectory() {
		try {
			return PathUtils.isDirectory(getPath());
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 判断资源是否是一个空目录
	 *
	 * @return 判断结果
	 */
	public default boolean isEmptyDirectory() {
		try {
			return PathUtils.isEmptyDirectory(getPath());
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 判断资源是否是一个连接
	 *
	 * @return 判断结果
	 */
	public default boolean isSymbolicLink() {
		try {
			return PathUtils.isSymbolicLink(getPath());
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 判断资源是否是一个隐藏文件或目录
	 *
	 * @return 判断结果
	 */
	public default boolean isHidden() {
		try {
			return PathUtils.isHidden(getPath());
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 获取相对路径的资源
	 *
	 * @param relativePath 相对路径
	 * @return {@link RichResource}实例
	 * @throws IOException I/O错误
	 */
	public default RichResource createRelativeAsRichResource(String relativePath) throws IOException {
		return new RichResourceImpl(createRelative(relativePath));
	}

	/**
	 * 获取代理的对象
	 *
	 * @return 代理的 {@link Resource} 对象
	 */
	public Resource delegating();

	/**
	 * 获取代理的对象
	 *
	 * @return 代理的 {@link Resource} 对象
	 */
	public default Resource toResource() {
		// 本方法可以被 ObjectToObjectConverter 使用
		return delegating();
	}

	/**
	 * 获取代理的对象的类型
	 *
	 * @return 代理的对象的类型
	 */
	public default Class<? extends Resource> getDelegatingType() {
		return delegating().getClass();
	}

}
