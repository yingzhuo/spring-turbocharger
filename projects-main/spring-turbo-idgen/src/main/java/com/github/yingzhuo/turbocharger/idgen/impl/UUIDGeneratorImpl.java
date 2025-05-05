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
package com.github.yingzhuo.turbocharger.idgen.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.enums.UuidLocalDomain;
import com.github.f4b6a3.uuid.enums.UuidNamespace;
import com.github.yingzhuo.turbocharger.idgen.UUIDGenerator;

import java.util.UUID;

/**
 * {@link UUIDGenerator} 默认实现
 *
 * @author 应卓
 * @since 3.4.5
 */
public class UUIDGeneratorImpl implements UUIDGenerator {

	@Override
	public UUID v1() {
		return UuidCreator.getTimeBased();
	}

	@Override
	public UUID v2(int localIdentifier) {
		return UuidCreator.getDceSecurity(UuidLocalDomain.LOCAL_DOMAIN_PERSON, localIdentifier);
	}

	@Override
	public UUID v3(String namespace) {
		return UuidCreator.getNameBasedMd5(UuidNamespace.NAMESPACE_URL, namespace);
	}

	@Override
	public UUID v4() {
		return UuidCreator.getRandomBased();
	}

	@Override
	public UUID v5(String namespace) {
		return UuidCreator.getNameBasedSha1(UuidNamespace.NAMESPACE_URL, namespace);
	}

	@Override
	public UUID v6() {
		return UuidCreator.getTimeOrdered();
	}

	@Override
	public UUID v7() {
		return UuidCreator.getTimeOrderedEpoch();
	}

}
