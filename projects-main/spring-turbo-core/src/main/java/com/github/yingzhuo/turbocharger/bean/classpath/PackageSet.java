/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.bean.classpath;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * 辅助工具封装多个搜索起点
 *
 * @author 应卓
 * @see ClassPathScanner
 * @since 3.5.3
 */
public final class PackageSet implements Iterable<String>, Serializable {

	private final SortedSet<String> innerSet = new TreeSet<>(Comparator.naturalOrder());

	/**
	 * 默认构造方法
	 */
	public PackageSet() {
		super();
	}

	/**
	 * 添加要扫描的包
	 *
	 * @param packages 包
	 * @return this
	 */
	public PackageSet acceptPackages(@Nullable String... packages) {
		if (packages != null) {
			Stream.of(packages)
				.filter(StringUtils::isNotBlank)
				.map(String::trim)
				.forEach(innerSet::add);
		}
		return this;
	}

	/**
	 * 添加要扫描的包
	 *
	 * @param packages 包
	 * @return this
	 */
	public PackageSet acceptPackages(@Nullable Package... packages) {
		if (packages != null) {
			Stream.of(packages)
				.filter(Objects::nonNull)
				.map(Package::getName)
				.forEach(innerSet::add);
		}
		return this;
	}

	/**
	 * 添加要扫描的基础类所在的包
	 *
	 * @param baseClasses 基础类
	 * @return this
	 */
	public PackageSet acceptBasePackageClasses(@Nullable Class<?>... baseClasses) {
		if (baseClasses != null) {
			Arrays.stream(baseClasses)
				.filter(Objects::nonNull)
				.map(c -> c.getPackage().getName())
				.forEach(innerSet::add);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<String> iterator() {
		return innerSet.iterator();
	}

	/**
	 * 清空所有数据
	 *
	 * @return this
	 */
	public PackageSet clear() {
		innerSet.clear();
		return this;
	}

	/**
	 * 判断是否为空
	 *
	 * @return 判断结果
	 * @see Set#isEmpty()
	 */
	public boolean isEmpty() {
		return innerSet.isEmpty();
	}

	/**
	 * 获取保存的包数量
	 *
	 * @return 保存的包数
	 * @see Set#size()
	 */
	public int size() {
		return innerSet.size();
	}

	/**
	 * 转换成{@link SortedSet}
	 *
	 * @return {@link SortedSet}实例
	 * @see SortedSet
	 * @see TreeSet
	 * @see Comparator#naturalOrder()
	 */
	public SortedSet<String> asSet() {
		return Collections.unmodifiableSortedSet(innerSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var strings = (PackageSet) o;
		return innerSet.equals(strings.innerSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(innerSet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return innerSet.toString();
	}

}
