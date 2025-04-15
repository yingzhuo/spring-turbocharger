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
package com.github.yingzhuo.turbocharger.core.resource;

import com.github.yingzhuo.turbocharger.util.HexUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * @author 应卓
 * @see ByteArrayResource
 * @since 3.3.2
 */
final class HexProtocolResolver implements ProtocolResolver {

	private static final String HEX_PREFIX = "hex:";

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if (location.startsWith(HEX_PREFIX)) {
			var value = location.substring(HEX_PREFIX.length());
			return new ByteArrayResource(HexUtils.decodeToBytes(value));
		}
		return null;
	}

}
