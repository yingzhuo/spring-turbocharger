package com.github.yingzhuo.turbocharger.bean;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.io.ApplicationResourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractImportingSupport
	implements ResourceLoaderAware, EnvironmentAware, BeanFactoryAware, BeanClassLoaderAware {

	private ResourceLoader resourceLoader = ApplicationResourceLoader.get();
	private Environment environment = new StandardEnvironment();
	private BeanFactory beanFactory = new DefaultListableBeanFactory();
	private ClassLoader beanClassLoader = Thread.currentThread().getContextClassLoader();

	public AbstractImportingSupport() {
		super();
	}

	public final Environment getEnvironment() {
		return this.environment;
	}

	@Override
	public final void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public final ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	@Override
	public final void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public final BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public final void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public final ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	// AnnotationMetadata相关
	// -----------------------------------------------------------------------------------------------------------------

	public final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation) {
		return getAnnotationAttributesSet(metadata, importingAnnotation, null);
	}

	public final Set<SmartAnnotationAttributes> getAnnotationAttributesSet(AnnotationMetadata metadata, Class<? extends Annotation> importingAnnotation, @Nullable Class<? extends Annotation> importingContainerAnnotation) {
		return ImportingUtils.getAnnotationAttributesSet(metadata, importingAnnotation, importingContainerAnnotation)
			.stream()
			.map(a -> new SmartAnnotationAttributes(environment, a))
			.collect(Collectors.toUnmodifiableSet());
	}

	public final String getImportingClassName(ClassMetadata metadata) {
		return ImportingUtils.getImportingClassName(metadata);
	}

	public final Class<?> getImportingClass(ClassMetadata metadata) {
		return ImportingUtils.getImportingClass(metadata);
	}

	public final Package getImportingClassPackage(ClassMetadata metadata) {
		return ImportingUtils.getImportingClassPackage(metadata);
	}

	@Nullable
	public final <A extends Annotation> A getAnnotationOfImportingClass(ClassMetadata metadata, Class<A> annotationType) {
		return ImportingUtils.getAnnotationOfImportingClass(metadata, annotationType);
	}

	// ResourceLoader相关
	// -----------------------------------------------------------------------------------------------------------------

	public final Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}

	public final String getResourceAsString(String location) {
		return getResourceAsString(location, (Charset) null);
	}

	public final String getResourceAsString(String location, @Nullable String charset) {
		return getResourceAsString(location, Charset.forName(charset != null ? charset : "UTF-8"));
	}

	public final String getResourceAsString(String location, @Nullable Charset charset) {
		try {
			return getResource(location).getContentAsString(charset != null ? charset : StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public final Stream<String> getResourceAsLines(String location) {
		return getResourceAsLines(location, (Charset) null);
	}

	public final Stream<String> getResourceAsLines(String location, @Nullable Charset charset) {
		return getResourceAsString(location, charset).lines();
	}

	public final Stream<String> getResourceAsLines(String location, @Nullable String charset) {
		return getResourceAsString(location, charset).lines();
	}

	public final byte[] getResourceAsBytes(String location) {
		try {
			return getResource(location).getContentAsByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public final InputStream getResourceAsInputStream(String location) {
		return getResourceAsInputStream(location, -1);
	}

	public final InputStream getResourceAsInputStream(String location, int bufferSize) {
		try {
			var in = getResource(location).getInputStream();
			return bufferSize > 0 ? new BufferedInputStream(in, bufferSize) : in;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	// BeanFactory相关
	// -----------------------------------------------------------------------------------------------------------------

	@Nullable
	public final <T> T getBean(Class<T> type, String beanName) {
		try {
			return beanFactory.getBean(beanName, type);
		} catch (BeansException e) {
			return null;
		}
	}

	@Nullable
	public final <T> T getBean(Class<T> type) {
		try {
			return beanFactory.getBean(type);
		} catch (BeansException e) {
			return null;
		}
	}

}
