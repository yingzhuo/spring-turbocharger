package com.github.yingzhuo.turbocharger.util.reflection;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class InstanceCache {

	private final Map<Class<?>, Object> map = new HashMap<>();

	@Nullable
	private final ApplicationContext applicationContext;

	@Nullable
	private final BeanFactory beanFactory;

	private InstanceCache(@Nullable ApplicationContext applicationContext, @Nullable BeanFactory beanFactory) {
		this.applicationContext = applicationContext;
		this.beanFactory = beanFactory;
	}

	public static InstanceCache newInstance() {
		return new InstanceCache(null, null);
	}

	public static InstanceCache newInstance(@Nullable ApplicationContext applicationContext) {
		return new InstanceCache(applicationContext, null);
	}

	public static InstanceCache newInstance(@Nullable BeanFactory beanFactory) {
		return new InstanceCache(null, beanFactory);
	}

	public InstanceCache add(Class<?> type, Object instance) {
		Assert.notNull(type, "type is required");
		Assert.notNull(instance, "instance is required");

		map.put(type, instance);
		return this;
	}

	public synchronized <T> T findOrCreate(Class<?> type) {
		return findOrCreate(type, new InstantiationExceptionSupplier(type));
	}

	public synchronized <T> T findOrCreate(Class<?> type,
										   Supplier<? extends RuntimeException> exceptionIfCannotCreateInstance) {
		Assert.notNull(type, "type is required");
		Assert.notNull(exceptionIfCannotCreateInstance, "exceptionIfCannotCreateInstance is required");

		Object instance = findFromApplicationContext(type);
		if (instance != null) {
			// spring托管的不放入map
			// 如果该bean是prototype类型，每次都需要创建一个新对象
			return (T) instance;
		}

		instance = findFromBeanFactory(type);
		if (instance != null) {
			return (T) instance;
		}

		instance = map.get(type);
		if (instance == null) {
			instance = InstanceUtils.newInstanceElseThrow(type, exceptionIfCannotCreateInstance);
			map.put(type, instance);
		}
		return (T) instance;
	}

	@Nullable
	private Object findFromApplicationContext(Class<?> type) {
		if (applicationContext == null) {
			return null;
		}

		final ObjectProvider provider = applicationContext.getBeanProvider(type);
		try {
			return provider.getIfAvailable();
		} catch (BeansException e) {
			return null;
		}
	}

	@Nullable
	private Object findFromBeanFactory(Class<?> type) {
		if (beanFactory == null) {
			return null;
		}

		try {
			return beanFactory.getBean(type);
		} catch (BeansException e) {
			return null;
		}
	}

}
