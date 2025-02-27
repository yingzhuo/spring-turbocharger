package buildlogic.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

class LicensePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.extensions.add(AddLicenseHeaderTask.TASK_NAME, new Config())
		project.getTasks()
			.register(AddLicenseHeaderTask.TASK_NAME, AddLicenseHeaderTask, project)
	}

	static class AddLicenseHeaderTask extends DefaultTask {
		public static final String TASK_NAME = 'addLicenseHeader'

		final Project project;

		@Inject
		AddLicenseHeaderTask(Project project) {
			this.project = project
			this.group = 'license'
			this.description = 'Adds license header to the source files'
		}

		@TaskAction
		void execute() {
			final var header = getJavaHeader()

			project.fileTree(project.rootDir) {
				include '**/*.java'
			}.each { file ->
				def content = file.text
				if (!content.startsWith(header)) {
					file.setText(header + content)
				}
				if (!content.endsWith('\n')) {
					file.setText(content + '\n')
				}
			}
		}

		private String getJavaHeader() {
			var config = project.extensions.getByName(TASK_NAME) as Config
			var header = config.javaHeader

			if (header.isBlank()) {
				var msg = 'java license header text is blank'
				logger.error(msg)
				throw new GradleException(msg)
			}

			if (!header.endsWith('\n')) {
				header += '\n'
			}
			return header
		}
	}

	static class Config implements Serializable {
		String javaHeader = ''
	}

}
