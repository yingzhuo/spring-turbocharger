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
package com.github.yingzhuo.turbocharger.util.io.resource;

import com.github.yingzhuo.turbocharger.util.keystore.KeyStoreType;

import java.io.InputStream;

/**
 * 基于PKCS#12格式的{@link org.springframework.core.io.Resource}
 *
 * @author 应卓
 * @see JKSResource
 * @see KeyStoreType#PKCS12
 * @since 3.5.0
 */
public class PKCS12Resource extends KeyStoreResource {

	public PKCS12Resource(InputStream in, String storepass) {
		super(in, KeyStoreType.PKCS12, storepass);
	}

}
