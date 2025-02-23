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

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException

class LicenseAddingPlugin extends AbstractPlugin {

	final static String TASK_NAME_ADD_JAVA_LICENSE_HEADER = "addLicenseHeader"

	@Override
	void apply(Project project) {
		super.setExtensionsBean(project, TASK_NAME_ADD_JAVA_LICENSE_HEADER, new AddJavaLicenseHeaderExt())

		project
			.getTasks()
			.register(TASK_NAME_ADD_JAVA_LICENSE_HEADER, DefaultTask) { task ->
				task.group = 'license'
				task.description = 'Adds license header for the Java source files'

				var config = getExtensionsBean(project, TASK_NAME_ADD_JAVA_LICENSE_HEADER) as AddJavaLicenseHeaderExt
				var header = config.javaHeader
				if (!header.endsWith('\n')) {
					header += '\n'
				}

				if (header.isBlank()) {
					throw new StopExecutionException('java license header text is blank')
				}

				doLast {
					project.fileTree(project.rootDir) {
						include '**/*.java'
					}.each { file ->
						def content = file.text
						if (!content.startsWith(header)) {
							file.setText(
								header + content
							)
						}
					}
				}
			}
	}

	static class AddJavaLicenseHeaderExt {
		String javaHeader = ''
	}

}
