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
package com.github.yingzhuo.turbocharger.util.collection;

import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @see ListFactories
 * @see SetFactories
 * @see StreamFactories
 * @see StringObjectMap
 * @since 1.1.2
 */
public final class StringStringMap extends HashMap<String, String> {

	/**
	 * 构造方法
	 */
	public StringStringMap() {
		super();
	}

	public static StringStringMap newInstance() {
		return new StringStringMap();
	}

	public StringStringMap add(String k, String v) {
		this.put(k, v);
		return this;
	}

	public StringStringMap add(String k1, String v1, String k2, String v2) {
		this.put(k1, v1);
		this.put(k2, v2);
		return this;
	}

	public StringStringMap add(String k1, String v1, String k2, String v2, String k3, String v3) {
		this.put(k1, v1);
		this.put(k2, v2);
		this.put(k3, v3);
		return this;
	}

	public StringStringMap add(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4) {
		this.put(k1, v1);
		this.put(k2, v2);
		this.put(k3, v3);
		this.put(k4, v4);
		return this;
	}

	public StringStringMap removeAll() {
		this.clear();
		return this;
	}

	public StringStringMap delete(@Nullable String... keys) {
		if (keys != null) {
			for (String key : keys) {
				if (key != null) {
					this.remove(key);
				}
			}
		}
		return this;
	}

	public Map<String, String> toUnmodifiable() {
		return Collections.unmodifiableMap(this);
	}

}
