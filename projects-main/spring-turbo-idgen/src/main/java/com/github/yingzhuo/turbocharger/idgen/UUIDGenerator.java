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
package com.github.yingzhuo.turbocharger.idgen;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.enums.UuidLocalDomain;
import com.github.f4b6a3.uuid.enums.UuidNamespace;

import java.util.UUID;

/**
 * @author 应卓
 * @since 3.4.3
 */
public interface UUIDGenerator {

	public default UUID v1() {
		return UuidCreator.getTimeBased();
	}

	public default UUID v2(int localIdentifier) {
		return UuidCreator.getDceSecurity(UuidLocalDomain.LOCAL_DOMAIN_PERSON, localIdentifier);
	}

	public default UUID v3(String namespace) {
		return UuidCreator.getNameBasedMd5(UuidNamespace.NAMESPACE_URL, namespace);
	}

	public default UUID v4() {
		return UuidCreator.getRandomBased();
	}

	public default UUID v5(String namespace) {
		return UuidCreator.getNameBasedSha1(UuidNamespace.NAMESPACE_URL, namespace);
	}

	public default UUID v6() {
		return UuidCreator.getTimeOrdered();
	}

	public default UUID v7() {
		return UuidCreator.getTimeOrderedEpoch();
	}

	/**
	 * 默认实现
	 */
	public static class Default implements UUIDGenerator {
	}

}
