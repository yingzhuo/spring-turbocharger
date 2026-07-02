package com.github.yingzhuo.turbocharger.util.spi;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see ServiceLoader
 * @see SPILoader#builder(Class)
 * @see SPILoader#builder(Class, ClassLoader)
 * @since 3.5.0
 */
public final class ServiceLoaderUtils {

	/**
	 * 私有构造方法
	 */
	private ServiceLoaderUtils() {
		super();
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType 要加载的抽象类型
	 * @param <T>        要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null, null);
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType  要加载的抽象类型
	 * @param classLoader 类加载器
	 * @param <T>         要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader) {
		return load(targetType, classLoader, null);
	}

	/**
	 * 加载SPI实例
	 *
	 * @param targetType  要加载的抽象类型
	 * @param classLoader 类加载器
	 * @param filter      过滤器
	 * @param <T>         要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable ClassLoader classLoader, @Nullable Predicate<Class<?>> filter) {
		Assert.notNull(targetType, "targetType must not be null");

		Predicate<Class<?>> predicateToUse = filter != null ? filter : c -> true;
		return ServiceLoader.load(targetType, classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader())
			.stream()
			.filter(p -> predicateToUse.test(p.type())).map(ServiceLoader.Provider::get);
	}

}
