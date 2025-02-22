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

import com.github.yingzhuo.turbocharger.bean.classpath.ClassDefinition;
import com.github.yingzhuo.turbocharger.util.function.GenericGenerator;
import org.springframework.core.env.Environment;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * RestClientInterface 生成器 <br>
 * 这是一个内部工具
 *
 * @author 应卓
 * @since 3.3.1
 */
class RestClientInterfaceGen implements GenericGenerator {

	private final ClassDefinition classDef;
	private final Environment environment;
	private final RestClientSupplier restClientSupplier;
	private final ArgumentResolversSupplier globalArgumentResolversSupplier;
	private final ArgumentResolversSupplier argumentResolversSupplier;

	public RestClientInterfaceGen(
		ClassDefinition classDef,
		Environment environment,
		RestClientSupplier restClientSupplier,
		ArgumentResolversSupplier globalArgumentResolversSupplier,
		ArgumentResolversSupplier argumentResolversSupplier) {
		this.classDef = classDef;
		this.environment = environment;
		this.restClientSupplier = restClientSupplier;
		this.globalArgumentResolversSupplier = globalArgumentResolversSupplier;
		this.argumentResolversSupplier = argumentResolversSupplier;
	}

	@Override
	public Object generate() {
		var restClient = restClientSupplier.get();
		var adapter = RestClientAdapter.create(restClient);

		var factoryBuilder = HttpServiceProxyFactory.builderFor(adapter)
			.embeddedValueResolver(environment::resolvePlaceholders);

		var argumentResolvers = argumentResolversSupplier.get();
		if (argumentResolvers != null) {
			for (var argumentResolver : argumentResolvers) {
				if (argumentResolver != null) {
					factoryBuilder.customArgumentResolver(argumentResolver);
				}
			}
		}

		var globalArgumentResolvers = globalArgumentResolversSupplier.get();
		if (globalArgumentResolvers != null) {
			for (var globalArgumentResolver : globalArgumentResolvers) {
				if (globalArgumentResolver != null) {
					factoryBuilder.customArgumentResolver(globalArgumentResolver);
				}
			}
		}

		return factoryBuilder.build()
			.createClient(classDef.getBeanClass());
	}

}
