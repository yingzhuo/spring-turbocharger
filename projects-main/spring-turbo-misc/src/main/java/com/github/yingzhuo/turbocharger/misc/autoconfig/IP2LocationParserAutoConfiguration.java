/*
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
 */
package com.github.yingzhuo.turbocharger.misc.autoconfig;

import com.github.yingzhuo.turbocharger.misc.ip.IP2LocationParser;
import com.github.yingzhuo.turbocharger.misc.ip.IP2LocationParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 3.5.6
 */
@ConditionalOnMissingBean(IP2LocationParser.class)
@ConditionalOnClass(name = "org.lionsoul.ip2region.DbSearcher")
public class IP2LocationParserAutoConfiguration {

	@Bean
	public IP2LocationParser ip2LocationParser() {
		return new IP2LocationParserImpl();
	}

}
