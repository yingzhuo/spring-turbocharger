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
package com.github.yingzhuo.turbocharger.jackson.util;

import com.fasterxml.jackson.databind.Module;
import com.github.yingzhuo.turbocharger.util.spi.ServiceLoaderUtils;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Predicate;

/**
 * Jackson模块加载工具
 *
 * @author 应卓
 * @see Module
 * @see <a href="https://github.com/FasterXML/jackson">Jackson官方文档</a>
 * @since 3.3.1
 */
public final class JacksonModuleUtils {

	/**
	 * 私有构造方法
	 */
	private JacksonModuleUtils() {
	}

	/**
	 * 加载classpath中被声明的所有的模块
	 *
	 * @return 模块(多个)
	 */
	public static List<Module> loadModules() {
		return loadModules(null);
	}

	/**
	 * 加载classpath中被声明的所有的模块
	 *
	 * @param filter 过滤用predicate
	 * @return 模块(多个)
	 */
	public static List<Module> loadModules(@Nullable Predicate<ServiceLoader.Provider<Module>> filter) {
		return ServiceLoaderUtils.load(Module.class, filter)
			.toList();
	}

}
