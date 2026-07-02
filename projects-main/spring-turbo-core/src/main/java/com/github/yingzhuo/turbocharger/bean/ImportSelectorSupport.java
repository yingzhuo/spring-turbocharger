package com.github.yingzhuo.turbocharger.bean;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Objects;

public abstract class ImportSelectorSupport extends AbstractImportingSupport implements ImportSelector {

	public ImportSelectorSupport() {
		super();
	}

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
