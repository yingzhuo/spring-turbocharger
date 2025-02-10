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

package com.github.yingzhuo.turbocharger.misc.tokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.lang.Nullable;

/**
 * @author 应卓
 * @since 3.3.1
 */
public class TokenizerServiceFactoryBean implements SmartFactoryBean<TokenizerService> {

	// 为何需要FactoryBean再过度一次。
	// 实际上cn.hutool.extra.tokenizer.TokenizerEngine也是一个门面的经典案列
	// 如果缺失依赖则会造成启动失败

	private static final Logger log = LoggerFactory.getLogger(TokenizerServiceFactoryBean.class);

	@Nullable
	@Override
	public TokenizerService getObject() {
		try {
			return new TokenizerServiceImpl();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Class<?> getObjectType() {
		return TokenizerService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public boolean isPrototype() {
		return false;
	}

	@Override
	public boolean isEagerInit() {
		return false;
	}
}
