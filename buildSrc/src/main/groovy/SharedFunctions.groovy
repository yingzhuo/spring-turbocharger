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
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException

import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime

/**
 * 构建逻辑的共享函数
 */
abstract class SharedFunctions {

	static String getTimestamp(String formatPattern = 'yyyyMMddHHmmss') {
		return LocalDateTime.now().format(formatPattern)
	}

	static List<String> getLeafProjectNames(Project rootProject) {
		return rootProject.allprojects
			.findAll {
				it.subprojects.isEmpty()
			}
			.collect {
				it.displayName
					.replace("project '", "")
					.replace("'", "")
			}
	}

	static String getGradleProperty(Project project, String propertyName, String defaultPropertyValue = null) {
		var value = project.providers.gradleProperty(propertyName).getOrElse(defaultPropertyValue)
		if (value == null) {
			throw new StopExecutionException("Cannot get value of name: ${propertyName}")
		}
		return value
	}

	static boolean getGradlePropertyAsBoolean(Project project, String propertyName, boolean defaultValue = false) {
		var booleanString = getGradleProperty(project, propertyName, "${defaultValue}")
		return Boolean.valueOf(booleanString)
	}

	static String getEnv(String name, String defaultValueIfMissing = '<no value>') {
		return Optional.of(System.getenv(name)).orElse(defaultValueIfMissing)
	}

	static boolean getEnvAsBoolean(String name, boolean defaultValue = false) {
		return getEnv(name, "${defaultValue}").toBoolean()
	}

	static void mkdir(File file) {
		file.mkdir()
	}

	static void copyFile(File src, File dest) {
		Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
	}
}
