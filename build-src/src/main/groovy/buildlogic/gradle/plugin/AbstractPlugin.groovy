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
package buildlogic.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 抽象插件类 <br>
 * 提供若干首相方法
 */
abstract class AbstractPlugin implements Plugin<Project> {

	protected void setExtensionsBean(Project project, String extensionsName, Object extensionsObject) {
		project.extensions.add(extensionsName, extensionsObject)
	}

	protected <T> T getExtensionsBean(Project project, String extensionsName) {
		var bean = project.extensions.findByName(extensionsName)
		if (bean == null) {
			throw new IllegalArgumentException("${extensionsName} not found")
		}
		return bean as T
	}

	protected <T> T getExtensionsBean(Project project, Class<T> extensionsBeanType) {
		var bean = project.extensions.findByType(extensionsBeanType)
		if (bean == null) {
			throw new IllegalArgumentException("type of ${extensionsBeanType.name} not found")
		}
		return bean;
	}

}
