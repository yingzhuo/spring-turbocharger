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
package com.github.yingzhuo.turbocharger.core.environment;

import com.github.yingzhuo.turbocharger.core.SpringUtils;
import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import java.nio.file.Path;
import java.util.Set;

/**
 * 内部工具，用来保存 {@link ApplicationContext} 实例等。
 *
 * @author 应卓
 * @see SpringUtils
 * @since 1.0.2
 */
public final class SpringApplicationHolders {

	@Nullable
	private static ApplicationContext APPLICATION_CONTEXT;

	@Nullable
	private static Path APPLICATION_HOME;

	@Nullable
	private static Set<Object> APPLICATION_SOURCES;

	@Nullable
	private static WebApplicationType APPLICATION_WEB_APPLICATION_TYPE;

	@Nullable
	private static Environment ENVIRONMENT;

	/**
	 * 私有构造方法
	 */
	private SpringApplicationHolders() {
		super();
	}

	@Nullable
	public static ApplicationContext getApplicationContext() {
		return APPLICATION_CONTEXT;
	}

	@Nullable
	public static Path getApplicationHome() {
		return APPLICATION_HOME;
	}

	@Nullable
	public static Set<Object> getApplicationSources() {
		return APPLICATION_SOURCES;
	}

	@Nullable
	public static WebApplicationType getApplicationWebApplicationType() {
		return APPLICATION_WEB_APPLICATION_TYPE;
	}

	@Nullable
	public static Environment getEnvironment() {
		return ENVIRONMENT;
	}

	/**
	 * 被SpringBoot回调的挂钩
	 *
	 * @see EnvironmentPostProcessor
	 * @see ApplicationListener
	 */
	private static class Hook implements EnvironmentPostProcessor, Ordered, ApplicationListener<ContextRefreshedEvent> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
			SpringApplicationHolders.APPLICATION_HOME = getAppHomeDir(application).toAbsolutePath();
			SpringApplicationHolders.APPLICATION_SOURCES = application.getAllSources();
			SpringApplicationHolders.APPLICATION_WEB_APPLICATION_TYPE = application.getWebApplicationType();
			SpringApplicationHolders.ENVIRONMENT = environment;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getOrder() {
			return LOWEST_PRECEDENCE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onApplicationEvent(ContextRefreshedEvent event) {
			SpringApplicationHolders.APPLICATION_CONTEXT = event.getApplicationContext();
		}

		private Path getAppHomeDir(SpringApplication springApplication) {
			var sourceClasses = springApplication.getAllSources()
				.stream()
				.filter(o -> o instanceof Class<?>)
				.map(o -> (Class<?>) o)
				.toList();

			var file = CollectionUtils.size(sourceClasses) == 1 ?
				new ApplicationHome(sourceClasses.get(0)).getDir() :
				new ApplicationHome().getDir();

			return file.toPath();
		}
	}

}
