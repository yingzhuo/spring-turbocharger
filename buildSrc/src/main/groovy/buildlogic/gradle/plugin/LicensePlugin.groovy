package buildlogic.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
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
			this.description = 'Add license header to the source files'
		}

		@TaskAction
		void execute() {
			var config = project.extensions.getByName(TASK_NAME) as Config
			var header = config.javaHeader
			if (!header.endsWith('\n')) {
				header += '\n'
			}

			if (header.isBlank()) {
				throw new StopExecutionException('java license header text is blank')
			}

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
	}

	static class Config implements Serializable {
		String javaHeader = ''
	}

}
