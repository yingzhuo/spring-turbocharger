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

package com.github.yingzhuo.turbocharger.misc.captcha.support;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.id.UUIDGenerators;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;

/**
 * {@link AccessKeyGenerator}的默认实现
 *
 * @author 应卓
 * @since 1.0.1
 */
public class SimpleAccessKeyGenerator implements AccessKeyGenerator, InitializingBean {

	//    @ApplicationName
	@Value("spring.application.name:unknown-application")
	private String applicationName;

	@Override
	public String generate() {
		return applicationName + "-captcha-access-key-" + UUIDGenerators.classic32();
	}

	@Override
	public void afterPropertiesSet() {
		this.applicationName = StringUtils.isBlank(this.applicationName) ? EMPTY : applicationName;
	}

}
