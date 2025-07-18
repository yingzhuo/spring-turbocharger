/*
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
 */
package com.github.yingzhuo.turbocharger.util;

import java.io.Serializable;

/**
 * {@link java.security.KeyStore}格式类型，本程序库只支持以下两种。<br>
 * 其中 <em>pkcs12</em> 为推荐格式
 *
 * <ul>
 *     <li>pkcs12</li>
 *     <li>jks</li>
 * </ul>
 *
 * @author 应卓
 * @since 3.3.1
 */
public enum KeyStoreType implements Serializable {

	/**
	 * PKCS#12格式, Java9以后此格式为默认。 推荐此格式，文件扩展名为 p12或pfx。
	 */
	PKCS12("pkcs12"),

	/**
	 * JKS，Java8及以前使用的格式。 文件扩展名为jks。
	 */
	JKS("jks");

	/**
	 * 字符串类型值
	 */
	private final String value;

	/**
	 * 私有构造方法
	 *
	 * @param stringValue 字符串类型值
	 */
	KeyStoreType(String stringValue) {
		this.value = stringValue;
	}

	public String getValue() {
		return value;
	}

}
