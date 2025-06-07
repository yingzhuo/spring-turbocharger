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
package com.github.yingzhuo.turbocharger.util.keystore;

import com.github.yingzhuo.turbocharger.core.ResourceUtils;
import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.cert.X509Certificate;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 3.5.0
 */
public final class KeyBundleFactories {

	/**
	 * 私有构造方法
	 */
	private KeyBundleFactories() {
		super();
	}

	public static KeyBundle ofPemContent(String pemContent, @Nullable String keypass) {
		var pc = PemContent.of(pemContent);
		return new KeyBundleImpl(pc.getCertificates(), pc.getPrivateKey(keypass));
	}

	public static KeyBundle loadPemResource(String resourceLocation, @Nullable String keypass) {
		return ofPemContent(ResourceUtils.readResourceAsString(resourceLocation, UTF_8), keypass);
	}

	public static KeyBundle loadStoreResource(
		String resourceLocation,
		KeyStoreFormat storeFormat,
		String storepass,
		String alias,
		String keypass
	) {
		var storeResource = ResourceUtils.loadResource(resourceLocation);
		try (var in = storeResource.getInputStream()) {
			var loaded = KeyStoreUtils.loadKeyStore(in, storeFormat, storepass);
			var cert = KeyStoreUtils.getCertificate(loaded, alias);
			var key = KeyStoreUtils.getKey(loaded, alias, keypass);
			return new KeyBundleImpl(List.of((X509Certificate) cert), key, alias);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
