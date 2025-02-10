package com.github.yingzhuo.turbocharger

import org.gradle.api.Plugin
import org.gradle.api.Project

class InformationPlugin implements Plugin<Project> {

	/*
 	 * 本插件没有工程上的意义，仅用于展示二进制日志的写法。
 	 * 以备将来参考
     */

	private static final String TASK_NAME_INFO = 'information'

	@Override
	void apply(Project project) {
		registerTaskInfo(project)
	}

	@SuppressWarnings('GrMethodMayBeStatic')
	private void registerTaskInfo(Project project) {
		project.extensions.add(TASK_NAME_INFO, new ConfigData())
		project.tasks.register(TASK_NAME_INFO) { task ->
			task.group = "help"
			task.description = "Show project information"
			task.doLast {
				var config = project.extensions.findByName(TASK_NAME_INFO) as ConfigData
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
