package com.github.yingzhuo.turbocharger.bean;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Objects;

/**
 * {@link ImportSelector} 支持类
 *
 * @author 应卓
 * @see ImportBeanDefinitionRegistrarSupport
 * @see org.springframework.context.annotation.ImportAware
 * @since 3.5.3
 */
public abstract class ImportSelectorSupport extends AbstractImportingSupport implements ImportSelector {

	/**
	 * 默认构造方法
	 */
	public ImportSelectorSupport() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] selectImports(AnnotationMetadata metadata) {
		try {
			return doSelectImports(metadata).stream()
				.filter(Objects::nonNull)
				.map(Class::getName)
				.distinct()
				.toArray(String[]::new);
		} catch (BeanCreationException e) {
			throw e;
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage(), e);
		}
	}

	protected abstract List<Class<?>> doSelectImports(AnnotationMetadata metadata) throws Exception;

}
