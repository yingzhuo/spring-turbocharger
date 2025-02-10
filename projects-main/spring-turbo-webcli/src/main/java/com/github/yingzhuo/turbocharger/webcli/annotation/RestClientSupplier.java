/*
 *
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
 *
 */

package com.github.yingzhuo.turbocharger.webcli.annotation;

import com.github.yingzhuo.turbocharger.webcli.error.NoopResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 3.3.1
 */
@FunctionalInterface
public interface RestClientSupplier extends Supplier<RestClient> {

	@Override
	public RestClient get();

	// -----------------------------------------------------------------------------------------------------------------

	public static class Default implements RestClientSupplier {
		public RestClient get() {
			return RestClient.builder()
				.defaultStatusHandler(NoopResponseErrorHandler.getInstance())
				.build();
		}
	}

}
