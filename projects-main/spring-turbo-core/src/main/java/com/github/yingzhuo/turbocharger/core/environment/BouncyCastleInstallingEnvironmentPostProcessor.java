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
package com.github.yingzhuo.turbocharger.core.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.security.Provider;
import java.security.Security;

import static org.springframework.util.ClassUtils.forName;
import static org.springframework.util.ClassUtils.getDefaultClassLoader;

/**
 * 尝试安装BC Provider <br>
 * <a href="https://www.bouncycastle.org/documentation/documentation-java/">Bouncy Castle官方文档</a>
 *
 * @author 应卓
 * @since 3.3.2
 */
public class BouncyCastleInstallingEnvironmentPostProcessor implements EnvironmentPostProcessor {

	private static final String BOUNCY_CASTLE_PROVIDER_CLASS = "org.bouncycastle.jce.provider.BouncyCastleProvider";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		try {
			var cls = forName(BOUNCY_CASTLE_PROVIDER_CLASS, getDefaultClassLoader());
			var ctor = cls.getConstructor();
			var provider = ctor.newInstance();
			Security.addProvider((Provider) provider);
		} catch (Throwable ignored) {
			// noop
		}
	}

}
