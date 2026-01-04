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
package com.github.yingzhuo.turbocharger.idgen;

import java.util.UUID;

/**
 * @author 应卓
 * @since 3.4.3
 */
public interface UUIDGenerator {

	public UUID v1();

	public UUID v2(int localIdentifier);

	public UUID v3(String namespace);

	public UUID v4();

	public UUID v5(String namespace);

	public UUID v6();

	public UUID v7();

}
