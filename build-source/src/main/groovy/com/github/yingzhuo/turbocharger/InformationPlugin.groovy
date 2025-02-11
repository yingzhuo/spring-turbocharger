package com.github.yingzhuo.turbocharger

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 显示项目信息插件
 */
class InformationPlugin extends AbstractPlugin implements Plugin<Project> {

	private static final String TASK_NAME_INFO = 'information'

	@Override
	void apply(Project project) {
		registerTaskInfo(project)
	}

	private void registerTaskInfo(Project project) {
		setExtensionsBean(project, TASK_NAME_INFO, new ConfigData())
		project.tasks.register(TASK_NAME_INFO) { task ->
			task.group = "help"
			task.description = "Show project information"
			task.doLast {
				ConfigData config = getExtensionsBean(project, TASK_NAME_INFO)
				println(config.text)
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * extensions of this plugin
	 */
	static class ConfigData implements Serializable {
		String text = '<no value>'
	}

}
