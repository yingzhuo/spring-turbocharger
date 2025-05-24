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
 * @since 3.5.0
 */
public final class KeyBundleBuilders {

	public static class PemBuilder {

		private String certificateLocation;
		private String certificateContent = "";
		private String privateKeyLocation;
		private String privateKeyContent = "";
		private String privateKeyPassword;

		PemBuilder() {
		}

		public PemBuilder certificateLocation(final String certificateLocation) {
			this.certificateLocation = certificateLocation;
			return this;
		}

		public PemBuilder certificateContent(final String certificateContent) {
			this.certificateContent = certificateContent;
			return this;
		}

		public PemBuilder privateKeyLocation(final String privateKeyLocation) {
			this.privateKeyLocation = privateKeyLocation;
			return this;
		}

		public PemBuilder privateKeyContent(final String privateKeyContent) {
			this.privateKeyContent = privateKeyContent;
			return this;
		}

		public PemBuilder privateKeyPassword(final String privateKeyPassword) {
			this.privateKeyPassword = privateKeyPassword;
			return this;
		}

		public KeyBundle build() {
			try {
				var factoryBean = new PemKeyBundleFactoryBean();
				factoryBean.setCertificateLocation(certificateLocation);
				factoryBean.setCertificateContent(certificateContent);
				factoryBean.setPrivateKeyLocation(privateKeyLocation);
				factoryBean.setPrivateKeyContent(privateKeyContent);
				factoryBean.setPrivateKeyPassword(privateKeyPassword);
				factoryBean.afterPropertiesSet();
				return factoryBean.getObject();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
	}

	public static class KeyStoreBuilder {
		private String location;
		private KeyStoreFormat format = KeyStoreFormat.PKCS12;
		private String storepass;
		private String alias;
		private String keypass;

		KeyStoreBuilder() {
		}

		public KeyStoreBuilder location(final String location) {
			this.location = location;
			return this;
		}

		public KeyStoreBuilder format(final KeyStoreFormat format) {
			this.format = format;
			return this;
		}

		public KeyStoreBuilder storepass(final String storepass) {
			this.storepass = storepass;
			return this;
		}

		public KeyStoreBuilder alias(final String alias) {
			this.alias = alias;
			return this;
		}

		public KeyStoreBuilder keypass(final String keypass) {
			this.keypass = keypass;
			return this;
		}

		public KeyBundle build() {
			try {
				var factoryBean = new StoreKeyBundleFactoryBean();
				factoryBean.setLocation(location);
				factoryBean.setFormat(format);
				factoryBean.setStorepass(storepass);
				factoryBean.setAlias(alias);
				factoryBean.setKeypass(keypass);
				factoryBean.afterPropertiesSet();
				return factoryBean.getObject();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
	}
}
