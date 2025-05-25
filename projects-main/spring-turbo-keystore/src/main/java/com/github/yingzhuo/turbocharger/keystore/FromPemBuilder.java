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

/**
 * @author 应卓
 * @see FromKeyStoreBuilder
 * @since 3.5.0
 */
public final class FromPemBuilder {

	private String certificateLocation;
	private String certificateContent = "";
	private String privateKeyLocation;
	private String privateKeyContent = "";
	private String privateKeyPassword;

	FromPemBuilder() {
		super();
	}

	public FromPemBuilder certificateLocation(String certificateLocation) {
		this.certificateLocation = certificateLocation;
		return this;
	}

	public FromPemBuilder certificateContent(String certificateContent) {
		this.certificateContent = certificateContent;
		return this;
	}

	public FromPemBuilder privateKeyLocation(String privateKeyLocation) {
		this.privateKeyLocation = privateKeyLocation;
		return this;
	}

	public FromPemBuilder privateKeyContent(String privateKeyContent) {
		this.privateKeyContent = privateKeyContent;
		return this;
	}

	public FromPemBuilder privateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
		return this;
	}

	public KeyBundle build() {
		try {
			var factory = new PemKeyBundleFactoryBean();
			factory.setCertificateLocation(certificateLocation);
			factory.setCertificateContent(certificateContent);
			factory.setPrivateKeyLocation(privateKeyLocation);
			factory.setPrivateKeyContent(privateKeyContent);
			factory.setPrivateKeyPassword(privateKeyPassword);
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}
