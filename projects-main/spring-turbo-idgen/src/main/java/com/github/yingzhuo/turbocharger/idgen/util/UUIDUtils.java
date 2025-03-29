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
package com.github.yingzhuo.turbocharger.idgen.util;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.idgen.UUIDGenerator;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * UUID生成工具
 *
 * @author 应卓
 * @since 3.4.4
 */
public final class UUIDUtils {

	/**
	 * 私有构造方法
	 */
	private UUIDUtils() {
		super();
	}

	public static String v1() {
		return v1(false);
	}

	public static String v1(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v1()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v2(int localIdentifier) {
		return v2(localIdentifier, false);
	}

	public static String v2(int localIdentifier, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v2(localIdentifier)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v3(String namespace) {
		return v3(namespace, false);
	}

	public static String v3(String namespace, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v3(namespace)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v4() {
		return v4(false);
	}

	public static String v4(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v4()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v5(String namespace) {
		return v5(namespace, false);
	}

	public static String v5(String namespace, boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v5(namespace)
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v6() {
		return v6(false);
	}

	public static String v6(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v6()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}

	public static String v7() {
		return v7(false);
	}

	public static String v7(boolean removeHyphen) {
		var uuid = SpringUtils.getRequiredBean(UUIDGenerator.class)
			.v7()
			.toString();
		return removeHyphen ? uuid.replaceAll("-", EMPTY) : uuid;
	}
}
