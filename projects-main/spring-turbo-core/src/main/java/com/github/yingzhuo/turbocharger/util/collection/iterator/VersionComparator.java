/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util.collection.iterator;

import com.github.yingzhuo.turbocharger.util.StringPool;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Objects;

/**
 * 版本号比较器
 *
 * @author 应卓
 * @see #getInstance()
 * @since 1.1.4
 */
public class VersionComparator implements Comparator<String> {

	/**
	 * 私有构造方法
	 */
	private VersionComparator() {
		super();
	}

	public static VersionComparator getInstance() {
		return SyncAvoid.INSTANCE;
	}

	/**
	 * 比较两个版本<br>
	 * null版本排在最小：即：
	 *
	 * <pre>
	 * compare(null, "v1") &lt; 0
	 * compare("v1", "v1") = 0
	 * compare(null, null) = 0
	 * compare("v1", null) &gt; 0
	 * compare("1.0.0", "1.0.2") &lt; 0
	 * compare("1.0.2", "1.0.2a") &lt; 0
	 * compare("1.13.0", "1.12.1c") &gt; 0
	 * compare("V0.0.20170102", "V0.0.20170101") &gt; 0
	 * </pre>
	 *
	 * @param version1 版本1
	 * @param version2 版本2
	 */
	@Override
	public int compare(@Nullable String version1, @Nullable String version2) {
		if (Objects.equals(version1, version2)) {
			return 0;
		}

		if (version1 == null) {// null视为最小版本，排在前
			return -1;
		}

		if (version2 == null) {
			return 1;
		}

		final String[] v1s = StringUtils.split(version1, StringPool.DOT);
		final String[] v2s = StringUtils.split(version2, StringPool.DOT);

		Objects.requireNonNull(v1s);
		Objects.requireNonNull(v2s);

		int diff = 0;
		int minLength = Math.min(v1s.length, v2s.length);// 取最小长度值
		String v1;
		String v2;
		for (int i = 0; i < minLength; i++) {
			v1 = v1s[i];
			v2 = v2s[i];
			// 先比较长度
			diff = v1.length() - v2.length();
			if (0 == diff) {
				diff = v1.compareTo(v2);
			}
			if (diff != 0) {
				// 已有结果，结束
				break;
			}
		}

		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		return (diff != 0) ? diff : v1s.length - v2s.length;
	}

	// -----------------------------------------------------------------------------------------------------------------

	// 延迟加载
	private static class SyncAvoid {
		public static final VersionComparator INSTANCE = new VersionComparator();
	}

}
