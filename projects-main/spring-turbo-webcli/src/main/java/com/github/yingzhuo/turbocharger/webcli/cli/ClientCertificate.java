/*
 *
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.webcli.cli;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * 客户端证书
 *
 * @author 应卓
 * @since 3.4.3
 */
public interface ClientCertificate extends Serializable {

	public static ClientCertificate of(String resourceLocation, String password) {
		return of(resourceLocation, KeyStoreFormat.PKCS12, password);
	}

	public static ClientCertificate of(String resourceLocation, @Nullable KeyStoreFormat keyStoreFormat, String password) {
		return of(
			ResourceUtils.loadResource(resourceLocation),
			keyStoreFormat,
			password
		);
	}

	public static ClientCertificate of(Resource resource, String password) {
		return of(resource, KeyStoreFormat.PKCS12, password);
	}

	public static ClientCertificate of(Resource resource, @Nullable KeyStoreFormat format, String password) {
		Assert.notNull(resource, "resource must not be null");
		Assert.hasLength(password, "password must not be empty");

		final var cert = new Default();
		cert.setResource(resource);
		cert.setKeyStoreFormat(Objects.requireNonNullElse(format, KeyStoreFormat.PKCS12));
		cert.setPassword(password);
		return cert;
	}

	/**
	 * 证书文件
	 *
	 * @return 证书文件
	 */
	public Resource getResource();

	/**
	 * 证书文件格式
	 *
	 * @return 证书文件格式
	 */
	public KeyStoreFormat getKeyStoreFormat();

	/**
	 * 证书密码
	 *
	 * @return 证书密码
	 */
	public String getPassword();

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 默认实现
	 */
	@Getter
	@Setter
	static class Default implements ClientCertificate {
		private Resource resource;
		private KeyStoreFormat keyStoreFormat = KeyStoreFormat.PKCS12;
		private String password;
	}

}
