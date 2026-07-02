package com.github.yingzhuo.turbocharger.bean.classpath;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

public class ClassPathScannerCore extends ClassPathScanningCandidateComponentProvider {

	public ClassPathScannerCore() {
		this(false);
	}

	public ClassPathScannerCore(boolean useDefaultFilters) {
		super(useDefaultFilters);
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		boolean isCandidate = false;
		if (beanDefinition.getMetadata().isIndependent()) {
			if (!beanDefinition.getMetadata().isAnnotation()) {
				isCandidate = true;
			}
		}
		return isCandidate;
	}

}
