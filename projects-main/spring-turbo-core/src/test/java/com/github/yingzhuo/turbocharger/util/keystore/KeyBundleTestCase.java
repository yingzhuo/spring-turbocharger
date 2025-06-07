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

import org.junit.jupiter.api.Test;

public class KeyBundleTestCase {

	@Test
	public void test1() {
		var kb = KeyBundleFactories.loadPemResource("classpath:rsa.pem", "123456");

		System.out.println(kb.getAlias());
		System.out.println(kb.getKeyPair());
	}

	@Test
	public void test2() {
		var kb = KeyBundleFactories.loadStoreResource(
			"classpath:test.pfx", KeyStoreFormat.PKCS12, "123456",  "rsa", "123456");

		System.out.println(kb.getAlias());
		System.out.println(kb.getKeyPair());
	}

}
