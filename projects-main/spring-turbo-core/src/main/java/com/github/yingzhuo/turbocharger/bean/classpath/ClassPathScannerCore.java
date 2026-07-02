package com.github.yingzhuo.turbocharger.bean.classpath;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * @author 应卓
 * @since 3.5.3
 */
public class ClassPathScannerCore extends ClassPathScanningCandidateComponentProvider {

	/**
	 * 默认构造方法
	 */
	public ClassPathScannerCore() {
		this(false);
	}

	/**
	 * 构造方法
	 *
	 * @param useDefaultFilters 是否启用默认类型过滤器
	 */
	public ClassPathScannerCore(boolean useDefaultFilters) {
		super(useDefaultFilters);
	}

	/**
	 * {@inheritDoc}
	 */
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
