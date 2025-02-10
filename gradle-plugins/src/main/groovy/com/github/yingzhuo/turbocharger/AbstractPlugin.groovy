package com.github.yingzhuo.turbocharger

import org.gradle.api.Plugin
import org.gradle.api.Project

import javax.annotation.Nonnull

/**
 * 插件基础类
 */
abstract class AbstractPlugin implements Plugin<Project> {

	protected void setExtensionsBean(Project project, String extensionsName, Object extensionsObject) {
		project.extensions.add(extensionsName, extensionsObject)
	}

	@Nonnull
	protected <T> T getExtensionsBean(Project project, String extensionsName) {
		var bean = project.extensions.findByName(extensionsName)
		if (bean == null) {
			throw new IllegalArgumentException("${extensionsName} not found")
		}
		return bean as T
	}

	@Nonnull
	protected <T> T getExtensionsBean(Project project, Class<T> extensionsBeanType) {
		var bean = project.extensions.findByType(extensionsBeanType)
		if (bean == null) {
			throw new IllegalArgumentException("type of ${extensionsBeanType.name} not found")
		}
		return bean;
	}

}
