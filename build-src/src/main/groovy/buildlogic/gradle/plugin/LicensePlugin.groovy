package buildlogic.gradle.plugin


import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException

class LicensePlugin extends AbstractPlugin implements Plugin<Project> {

	final static String TASK_NAME_ADD_JAVA_LICENSE_HEADER = "addJavaLicenseHeader"

	@Override
	void apply(Project project) {
		super.setExtensionsBean(project, TASK_NAME_ADD_JAVA_LICENSE_HEADER, new AddJavaLicenseHeaderExt())

		project
			.getTasks()
			.register(TASK_NAME_ADD_JAVA_LICENSE_HEADER, DefaultTask) { task ->
				task.group = 'license'
				task.description = 'Adds license header for the Java source files'

				var config = getExtensionsBean(project, TASK_NAME_ADD_JAVA_LICENSE_HEADER) as AddJavaLicenseHeaderExt
				var header = config.headerText

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

	static class AddJavaLicenseHeaderExt implements Serializable {
		String headerText = ''
	}

}
