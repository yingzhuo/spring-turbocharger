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
package com.github.yingzhuo.turbocharger.autoconfiguration;

import com.github.yingzhuo.turbocharger.util.crypto.keystore.KeyStoreFormatConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置类 (杂项)
 *
 * @author 应卓
 * @since 3.3.2
 */
public class MiscellaneousAutoConfiguration {

	/**
	 * 配置 {@link KeyStoreFormatConverter} 实例
	 *
	 * @return {@link KeyStoreFormatConverter} 实例
	 * @see KeyStoreFormatConverter
	 */
	@Bean
	@ConditionalOnMissingBean
	public KeyStoreFormatConverter keyStoreFormatConverter() {
		return KeyStoreFormatConverter.getInstance();
	}

}
