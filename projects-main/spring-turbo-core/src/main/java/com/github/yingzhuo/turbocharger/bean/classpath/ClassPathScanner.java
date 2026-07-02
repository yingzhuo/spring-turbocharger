package com.github.yingzhuo.turbocharger.bean.classpath;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class ClassPathScanner {

	private final ClassPathScannerCore core;
	private ClassLoader classLoader = ClassPathScanner.class.getClassLoader();

	public ClassPathScanner() {
		this(false);
	}

	public ClassPathScanner(boolean useDefaultFilters) {
		this.core = new ClassPathScannerCore(useDefaultFilters);
	}

	public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
		core.setResourceLoader(Objects.requireNonNullElseGet(resourceLoader, ApplicationResourceLoader::get));
	}

	public void setEnvironment(@Nullable Environment environment) {
		core.setEnvironment(Objects.requireNonNullElseGet(environment, StandardEnvironment::new));
	}

	public void setClassLoader(ClassLoader classLoader) {
		Assert.notNull(classLoader, "classLoader must not be null");
		this.classLoader = classLoader;
	}

	public void resetFilters() {
		core.resetFilters(false);
	}

	public void resetFilters(boolean useDefaultFilters) {
		core.resetFilters(useDefaultFilters);
	}

	public void addIncludeFilters(@Nullable TypeFilter... includeFilters) {
		if (includeFilters != null) {
			Stream.of(includeFilters)
				.filter(Objects::nonNull)
				.forEach(core::addIncludeFilter);
		}
	}

	public void addExcludeFilters(@Nullable TypeFilter... excludeFilters) {
		if (excludeFilters != null) {
			Stream.of(excludeFilters)
				.filter(Objects::nonNull)
				.forEach(core::addExcludeFilter);
		}
	}

	public Set<GenericBeanDefinition> scan(@Nullable PackageSet packageSet) {
		if (packageSet == null || packageSet.isEmpty()) {
			return Set.of();
		}

		var set = new HashSet<GenericBeanDefinition>();

		for (var basePackage : packageSet) {
			core.findCandidateComponents(basePackage)
				.stream()
				.map(bd -> bd instanceof GenericBeanDefinition g ? g : new GenericBeanDefinition(bd))
				.forEach(set::add);
		}

		// 强行初始化clazz
		for (var beanDef : set) {
			var className = beanDef.getBeanClassName();
			if (className != null) {
				var clazz = ClassUtils.resolveClassName(className, this.classLoader);
				beanDef.setBeanClass(clazz);
			}
		}

		return set;
	}

}
