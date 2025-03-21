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
package com.github.yingzhuo.turbocharger.util.id;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;
import static com.github.yingzhuo.turbocharger.util.StringPool.HYPHEN;
import static com.github.yingzhuo.turbocharger.util.id.IDGeneratorHelper.*;
import static java.util.UUID.randomUUID;

/**
 * UUID生成工具
 *
 * @author 应卓
 * @since 3.4.1
 */
@Deprecated
public final class UUIDGenerators {

	/**
	 * 私有构造方法
	 */
	private UUIDGenerators() {
	}

	public static String v4(boolean removeHyphen) {
		var uuid = randomUUID().toString();
		return removeHyphen ? uuid.replaceAll(HYPHEN, EMPTY) : uuid;
	}

	public static String classic32() {
		return v4(true);
	}

	public static String classic36() {
		return v4(false);
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static String timeBased32() {
		return timeBased(true);
	}

	public static String timeBased36() {
		return timeBased(false);
	}

	public static String timeBased(boolean removeHyphen) {
		var builder = new StringBuilder(removeHyphen ? 32 : 36);

		builder.append(getFormattedHiTime());
		if (!removeHyphen) {
			builder.append(HYPHEN);
		}

		builder.append(getFormattedLoTime());
		if (!removeHyphen) {
			builder.append(HYPHEN);
		}

		builder.append(getFormattedIP());
		if (!removeHyphen) {
			builder.append(HYPHEN);
		}

		builder.append(getFormattedJVM());
		if (!removeHyphen) {
			builder.append(HYPHEN);
		}

		builder.append(getFormattedCount());
		return builder.toString();
	}

}
