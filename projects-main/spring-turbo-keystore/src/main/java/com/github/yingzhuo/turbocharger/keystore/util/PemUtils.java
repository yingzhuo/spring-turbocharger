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
package com.github.yingzhuo.turbocharger.keystore.util;

import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.Key;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static com.github.yingzhuo.turbocharger.util.io.IOExceptionUtils.toUnchecked;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * PEM相关工具 <br>
 *
 * @author 应卓
 * @see PemContent
 * @see <a href="https://www.openssl.org/">OpenSSL官方文档</a>
 * @see <a href="https://en.wikipedia.org/wiki/X.509">X509 wiki</a>
 * @see <a href="https://en.wikipedia.org/wiki/PKCS_8">PKCS#8 wiki</a>
 * @since 3.3.1
 */
@SuppressWarnings("unchecked")
public final class PemUtils {

	/**
	 * 私有构造方法
	 */
	private PemUtils() {
		super();
	}

	/**
	 * 文件转换为 {@link PemContent} 实例
	 *
	 * @param path 文件
	 * @return PemContent 实例
	 */
	public static PemContent load(Path path) {
		notNull(path, "path is null");

		try {
			var content = PemContent.load(path);
			notNull(content, "content is null");
			return content;
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 文件转换为 {@link PemContent} 实例
	 *
	 * @param file 文件
	 * @return PemContent 实例
	 */
	public static PemContent load(File file) {
		notNull(file, "file is null");
		return load(file.toPath());
	}

	/**
	 * 资源转换为 {@link PemContent} 实例
	 *
	 * @param resource 资源
	 * @return PemContent 实例
	 */
	public static PemContent load(Resource resource) {
		notNull(resource, "resource is null");
		try {
			var content = PemContent.of(resource.getContentAsString(UTF_8));
			notNull(content, "content is null");
			return content;
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取证书 <br>
	 * <em>注意: 必须是X509格式</em>
	 *
	 * @param text PEM文件内容
	 * @return 证书
	 */
	public static X509Certificate readX509Certificate(String text) {
		hasText(text, "text is null or blank");

		var pemContent = PemContent.of(text);
		var certs = pemContent.getCertificates();
		if (certs.size() == 1) {
			return certs.get(0);
		}

		throw new IllegalStateException("cannot read a certificate chain");
	}

	/**
	 * 读取证书 <br>
	 * <em>注意: 必须是X509格式</em>
	 *
	 * @param resource PEM文件
	 * @return 证书
	 */
	public static X509Certificate readX509Certificate(Resource resource) {
		notNull(resource, "resource is null");

		try {
			return readX509Certificate(resource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取证书链
	 *
	 * @param text PEM文件内容
	 * @return 证书
	 */
	public static List<X509Certificate> readX509Certificates(String text) {
		hasText(text, "text is null or blank");

		var pemContent = PemContent.of(text);
		return new ArrayList<>(pemContent.getCertificates());
	}

	/**
	 * 读取证书链
	 *
	 * @param resource PEM文件
	 * @return 证书
	 */
	public static List<X509Certificate> readX509Certificates(Resource resource) {
		notNull(resource, "resource is null");

		try {
			return readX509Certificates(resource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取私钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param text PEM文件内容
	 * @param <T>  私钥类型泛型
	 * @return 私钥
	 */
	public static <T extends PrivateKey> T readPkcs8PrivateKey(String text) {
		return readPkcs8PrivateKey(text, null);
	}

	/**
	 * 读取私钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param resource PEM文件
	 * @param <T>      私钥类型泛型
	 * @return 私钥
	 */
	public static <T extends PrivateKey> T readPkcs8PrivateKey(Resource resource) {
		notNull(resource, "resource is null");

		try {
			return readPkcs8PrivateKey(resource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取私钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param text     PEM文件内容
	 * @param password 私钥密码
	 * @param <T>      私钥类型泛型
	 * @return 私钥
	 */
	public static <T extends PrivateKey> T readPkcs8PrivateKey(String text, @Nullable String password) {
		return (T) readPkcs8Key(text, password);
	}

	/**
	 * 读取私钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param resource PEM文件
	 * @param password 私钥密码
	 * @param <T>      私钥类型泛型
	 * @return 私钥
	 */
	public static <T extends PrivateKey> T readPkcs8PrivateKey(Resource resource, @Nullable String password) {
		notNull(resource, "resource is null");

		try {
			return readPkcs8PrivateKey(resource.getContentAsString(UTF_8), password);
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取秘钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param text PEM文件内容
	 * @param <T>  秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T readPkcs8Key(String text) {
		return readPkcs8Key(text, null);
	}

	/**
	 * 读取秘钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param resource PEM文件
	 * @param <T>      秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T readPkcs8Key(Resource resource) {
		notNull(resource, "resource is null");

		try {
			return readPkcs8Key(resource.getContentAsString(UTF_8));
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

	/**
	 * 读取秘钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param text     PEM文件内容
	 * @param password 秘钥，没有秘钥可以为{@code null}
	 * @param <T>      秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T readPkcs8Key(String text, @Nullable String password) {
		hasText(text, "text is null or blank");

		var pem = PemContent.of(text);
		return (T) pem.getPrivateKey(password);
	}

	/**
	 * 读取秘钥 <br>
	 * <em>注意: 必须是PKCS8格式</em>
	 *
	 * @param resource PEM文件内容
	 * @param password 秘钥，没有秘钥可以为{@code null}
	 * @param <T>      秘钥类型泛型
	 * @return 秘钥
	 */
	public static <T extends Key> T readPkcs8Key(Resource resource, @Nullable String password) {
		notNull(resource, "resource is null");

		try {
			return readPkcs8Key(resource.getContentAsString(UTF_8), password);
		} catch (IOException e) {
			throw toUnchecked(e);
		}
	}

}
