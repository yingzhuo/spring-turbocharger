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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * {@link KeyBundle} 实现
 *
 * @author 应卓
 * @since 3.5.0
 */
@SuppressWarnings("unchecked")
class KeyBundleImpl implements KeyBundle {

	@NonNull
	private final List<X509Certificate> certificates;

	@NonNull
	private final Key key;

	@Nullable
	private final String alias;


	KeyBundleImpl(List<X509Certificate> certificates, Key key, @Nullable String alias) {
		this.certificates = certificates;
		this.key = key;
		this.alias = alias;
	}

	@Nullable
	@Override
	public String getAlias() {
		return this.alias;
	}

	@Override
	public List<X509Certificate> getCertificateChain() {
		return this.certificates;
	}

	@Override
	public <T extends Key> T getKey() {
		return (T) this.key;
	}

}
