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
package com.github.yingzhuo.turbocharger.keystore;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see FromPemBuilder
 * @since 3.5.0
 */
public final class FromKeyStoreBuilder {

	@Nullable
	private String location;

	@NonNull
	private KeyStoreFormat format = KeyStoreFormat.PKCS12;

	@NonNull
	private String storepass = "changeit";

	@Nullable
	private String alias;

	@NonNull
	private String keypass = storepass;

	FromKeyStoreBuilder() {
		super();
	}

	public FromKeyStoreBuilder location(String location) {
		this.location = location;
		return this;
	}

	public FromKeyStoreBuilder format(KeyStoreFormat format) {
		this.format = format;
		return this;
	}

	public FromKeyStoreBuilder defaultStoreFormat() {
		return format(KeyStoreFormat.PKCS12);
	}

	public FromKeyStoreBuilder storepass(String storepass) {
		this.storepass = storepass;
		return this;
	}

	public FromKeyStoreBuilder alias(String alias) {
		this.alias = alias;
		return this;
	}

	public FromKeyStoreBuilder keypass(String keypass) {
		this.keypass = keypass;
		return this;
	}

	public KeyBundle build() {
		Assert.notNull(location, "location must not be null");
		Assert.notNull(format, "format must not be null");
		Assert.hasText(alias, "alias must not be empty");

		try {
			var factory = new StoreKeyBundleFactoryBean();
			factory.setLocation(location);
			factory.setFormat(format);
			factory.setStorepass(storepass);
			factory.setAlias(alias);
			factory.setKeypass(keypass);
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
