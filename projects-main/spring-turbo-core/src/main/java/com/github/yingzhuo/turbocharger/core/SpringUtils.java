package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.core.environment.SpringApplicationHolders;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public final class SpringUtils {

	public static final Supplier<? extends RuntimeException> NOT_SUPPORTED =
		() -> new UnsupportedOperationException("operation not supported yet");

	private SpringUtils() {
		super();
	}

	public static ApplicationContext getApplicationContext() {
		return Optional.ofNullable(SpringApplicationHolders.getApplicationContext())
			.orElseThrow(NOT_SUPPORTED);
	}

	public static Path getApplicationHome() {
		return Optional.ofNullable(SpringApplicationHolders.getApplicationHome())
			.orElseThrow(NOT_SUPPORTED);
	}

	public static String getApplicationHomeAsString() {
		return getApplicationHome().toString();
	}

	public static Set<Object> getApplicationSources() {
		return Optional.ofNullable(SpringApplicationHolders.getApplicationSources())
			.orElseGet(HashSet::new);
	}

	public static WebApplicationType getApplicationWebApplicationType() {
		return Optional.ofNullable(SpringApplicationHolders.getApplicationWebApplicationType())
			.orElseThrow(NOT_SUPPORTED);
	}

	public static String getApplicationContextId() {
		var id = getApplicationContext().getId();
		return Optional.ofNullable(id)
			.orElseThrow(NOT_SUPPORTED);
	}

	public static BeanDefinitionRegistry getBeanDefinitionRegistry() {
		return (BeanDefinitionRegistry) getApplicationContext().getAutowireCapableBeanFactory();
	}

	public static Environment getEnvironment() {
		return Optional.ofNullable(SpringApplicationHolders.getEnvironment())
			.orElseThrow(NOT_SUPPORTED);
	}

	public static ApplicationArguments getApplicationArguments() {
		return getApplicationContext().getBean(ApplicationArguments.class);
	}

	public static ConversionService getConversionService() {
		return getApplicationContext().getBean(ConversionService.class);
	}

	public static ApplicationEventPublisher getApplicationEventPublisher() {
		return getApplicationContext();
	}

	public static Validator getValidator() {
		return getApplicationContext().getBean(Validator.class);
	}

	public static MessageSource getMessageSource() {
		return getApplicationContext();
	}

	public static <T> Optional<T> getBean(Class<T> beanType) {
		Assert.notNull(beanType, "beanType is required");

		try {
			return Optional.of(getApplicationContext().getBean(beanType));
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	public static <T> Optional<T> getBean(Class<T> beanType, String beanName) {
		Assert.notNull(beanType, "beanType is required");
		Assert.hasText(beanName, "beanName is required");

		try {
			return Optional.of(getApplicationContext().getBean(beanName, beanType));
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

	public static <T> T getRequiredBean(Class<T> beanType) {
		return getBean(beanType)
			.orElseThrow(NOT_SUPPORTED);
	}

	public static <T> T getRequiredBean(Class<T> beanType, String beanName) {
		return getBean(beanType, beanName)
			.orElseThrow(NOT_SUPPORTED);
	}

	public static <T> List<T> getBeanList(final Class<T> beanType) {
		return getApplicationContext()
			.getBeansOfType(beanType)
			.values()
			.stream()
			.toList();
	}

}
