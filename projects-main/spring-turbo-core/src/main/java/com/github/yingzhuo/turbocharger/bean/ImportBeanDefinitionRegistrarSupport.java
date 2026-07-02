package com.github.yingzhuo.turbocharger.bean;

import com.github.yingzhuo.turbocharger.bean.classpath.ClassPathScanner;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public abstract class ImportBeanDefinitionRegistrarSupport extends AbstractImportingSupport implements ImportBeanDefinitionRegistrar {

	public ImportBeanDefinitionRegistrarSupport() {
		super();
	}

	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) {
		try {
			doRegister(metadata, registry, beanNameGen);
		} catch (BeanCreationException e) {
			throw e;
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage(), e);
		}
	}

	@Override
	public final void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// 不允许覆盖此方法
	}

	protected abstract void doRegister(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGen) throws Exception;

	// ClassPathScanner相关
	// -----------------------------------------------------------------------------------------------------------------

	public final ClassPathScanner createClassPathScanner() {
		return createClassPathScanner(false);
	}

	public final ClassPathScanner createClassPathScanner(boolean useDefaultFilters) {
		var scanner = new ClassPathScanner(useDefaultFilters);
		scanner.setClassLoader(getBeanClassLoader());
		scanner.setResourceLoader(getResourceLoader());
		scanner.setEnvironment(getEnvironment());
		return scanner;
	}

}
