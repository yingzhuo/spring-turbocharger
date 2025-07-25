/*
 * Copyright 2022-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 实例缓存
 *
 * @author 应卓
 * @see InstanceUtils
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class InstanceCache {

	private final Map<Class<?>, Object> map = new HashMap<>();

	@Nullable
	private final ApplicationContext applicationContext;

	@Nullable
	private final BeanFactory beanFactory;

	/**
	 * 私有构造方法
	 *
	 * @param applicationContext Spring上下文，可为 {@code null}
	 * @param beanFactory        beanFactory实例，可为 {@code null}
	 */
	private InstanceCache(@Nullable ApplicationContext applicationContext, @Nullable BeanFactory beanFactory) {
		this.applicationContext = applicationContext;
		this.beanFactory = beanFactory;
	}

	/**
	 * 创建实例缓存实例
	 *
	 * @return InstanceCache
	 */
	public static InstanceCache newInstance() {
		return new InstanceCache(null, null);
	}

	/**
	 * 创建实例缓存实例
	 *
	 * @param applicationContext Spring上下文，可为 {@code null}
	 * @return InstanceCache
	 */
	public static InstanceCache newInstance(@Nullable ApplicationContext applicationContext) {
		return new InstanceCache(applicationContext, null);
	}

	/**
	 * 创建实例缓存实例
	 *
	 * @param beanFactory BeanFactory实例，可为 {@code null}
	 * @return InstanceCache
	 */
	public static InstanceCache newInstance(@Nullable BeanFactory beanFactory) {
		return new InstanceCache(null, beanFactory);
	}

	/**
	 * 用户初始化缓存
	 *
	 * @param type     要缓存的类型 (不可为null)
	 * @param instance 实例 (不可为null)
	 * @return InstanceCache本身
	 */
	public InstanceCache add(Class<?> type, Object instance) {
		Assert.notNull(type, "type is required");
		Assert.notNull(instance, "instance is required");

		map.put(type, instance);
		return this;
	}

	/**
	 * 查找或尝试创建对象
	 *
	 * @param type 实例的类型
	 * @param <T>  实例泛型
	 * @return 实例值
	 */
	public synchronized <T> T findOrCreate(Class<?> type) {
		return findOrCreate(type, new InstantiationExceptionSupplier(type));
	}

	/**
	 * 查找或尝试创建对象
	 *
	 * @param type                            实例的类型
	 * @param exceptionIfCannotCreateInstance 在尝试创建对象失败时异常提供器
	 * @param <T>                             实例泛型
	 * @return 实例值
	 */
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
