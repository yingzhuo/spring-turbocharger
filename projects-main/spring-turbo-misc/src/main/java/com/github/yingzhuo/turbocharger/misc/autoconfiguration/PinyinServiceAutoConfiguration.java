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
package com.github.yingzhuo.turbocharger.misc.autoconfiguration;

import com.github.yingzhuo.turbocharger.misc.pinyin.PinyinService;
import com.github.yingzhuo.turbocharger.misc.pinyin.PinyinServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 3.1.0
 */
@AutoConfiguration
@ConditionalOnClass(name = "net.sourceforge.pinyin4j.PinyinHelper")
public class PinyinServiceAutoConfiguration {

	@Bean
	public PinyinService pinyinService() {
		return new PinyinServiceImpl();
	}

}
