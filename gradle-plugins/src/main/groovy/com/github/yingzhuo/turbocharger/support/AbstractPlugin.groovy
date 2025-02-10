package com.github.yingzhuo.turbocharger.support

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 插件基础类
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
