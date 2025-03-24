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
package com.github.yingzhuo.turbocharger.core.env;

import com.github.yingzhuo.turbocharger.bean.injection.ApplicationHome;
import com.github.yingzhuo.turbocharger.bean.injection.ApplicationParentPid;
import com.github.yingzhuo.turbocharger.bean.injection.ApplicationPid;
import com.github.yingzhuo.turbocharger.bean.injection.ApplicationProcessUser;
import com.github.yingzhuo.turbocharger.util.CurrentProcess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Optional;

/**
 * 杂项设置
 *
 * @author 应卓
 * @see ApplicationHome
 * @see ApplicationPid
 * @see ApplicationParentPid
 * @see ApplicationProcessUser
 * @since 3.3.1
 */
public class MiscellaneousPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		final var variables = new HashMap<String, Object>();
		variables.put("spring.process.pid", CurrentProcess.pid());
		variables.put("spring.process.parent-pid", CurrentProcess.parentPid()); // -1 则表示 null
		variables.put("spring.process.user", CurrentProcess.user());

		Optional.ofNullable(SpringApplicationHolders.getApplicationHome()).ifPresent(
			home -> variables.put("spring.application.home", home.toAbsolutePath().toString())
		);

		var propertySource = new MapPropertySource(
			"spring-turbo-miscellaneous",
			variables
		);

		environment.getPropertySources()
			.addLast(propertySource);
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE - 100;
	}

}
