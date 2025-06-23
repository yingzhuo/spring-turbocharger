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
package com.github.yingzhuo.turbocharger.webcli.cli;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import com.github.yingzhuo.turbocharger.util.KeyStoreType;
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

	/**
	 * 创建实例
	 *
	 * @param resourceLocation 证书资源位置
	 * @param format           证书格式
	 * @param storePassword    store密码
	 * @param keyPassword      key密码
	 * @return 实例
	 */
	public static ClientCertificate of(String resourceLocation, @Nullable KeyStoreType format, String storePassword, String keyPassword) {
		return of(
			(Resource) ResourceUtils.loadResource(resourceLocation),
			format,
			storePassword,
			keyPassword
		);
	}

	/**
	 * 创建实例
	 *
	 * @param resource      证书资源
	 * @param format        证书格式
	 * @param storePassword store密码
	 * @param keyPassword   key密码
	 * @return 实例
	 */
	public static ClientCertificate of(Resource resource, @Nullable KeyStoreType format, String storePassword, String keyPassword) {
		Assert.notNull(resource, "resource must not be null");
		Assert.notNull(storePassword, "storePassword must not be null");
		Assert.notNull(keyPassword, "keyPassword must not be null");

		final var cert = new Default();
		cert.setResource(resource);
		cert.setKeyStoreType(Objects.requireNonNullElse(format, KeyStoreType.PKCS12));
		cert.setStorePassword(storePassword);
		cert.setKeyPassword(keyPassword);
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
	public KeyStoreType getKeyStoreType();

	/**
	 * KeyStore密码
	 *
	 * @return KeyStore密码
	 */
	public String getStorePassword();

	/**
	 * Key密码
	 *
	 * @return Key密码
	 */
	public String getKeyPassword();

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 默认实现
	 */
	@Getter
	@Setter
	class Default implements ClientCertificate {
		private Resource resource;
		private KeyStoreType keyStoreType = KeyStoreType.PKCS12;
		private String storePassword;
		private String keyPassword;
	}

}
