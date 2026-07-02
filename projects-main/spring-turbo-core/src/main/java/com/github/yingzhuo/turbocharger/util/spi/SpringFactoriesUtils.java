package com.github.yingzhuo.turbocharger.util.spi;

import org.jspecify.annotations.Nullable;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see SPILoader#builder(Class)
 * @see SPILoader#builder(Class, ClassLoader)
 * @since 3.5.0
 */
public final class SpringFactoriesUtils {

	/**
	 * 私有构造方法
	 */
	private SpringFactoriesUtils() {
		super();
	}

	/**
	 * 加载SPI
	 *
	 * @param targetType 要加载的抽象类型
	 * @param <T>        要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType) {
		return load(targetType, null, null, null);
	}

	/**
	 * 加载SPI
	 *
	 * @param targetType                      要加载的抽象类型
	 * @param springFactoriesResourceLocation 配置文件位置
	 * @param <T>                             要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation) {
		return load(targetType, springFactoriesResourceLocation, null, null);
	}

	/**
	 * 加载SPI
	 *
	 * @param targetType                      要加载的抽象类型
	 * @param springFactoriesResourceLocation 配置文件位置
	 * @param classLoader                     类加载器实例
	 * @param <T>                             要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation, @Nullable ClassLoader classLoader) {
		return load(targetType, springFactoriesResourceLocation, classLoader, null);
	}

	/**
	 * 加载SPI
	 *
	 * @param targetType                      要加载的抽象类型
	 * @param springFactoriesResourceLocation 配置文件位置
	 * @param classLoader                     类加载器实例
	 * @param filter                          过滤器
	 * @param <T>                             要加载的抽象类型
	 * @return SPI实例
	 */
	public static <T> Stream<T> load(Class<T> targetType, @Nullable String springFactoriesResourceLocation, @Nullable ClassLoader classLoader, @Nullable Predicate<Class<?>> filter) {
		Assert.notNull(targetType, "targetType must not be null");

		Predicate<Class<?>> predicateToUse = filter != null ? filter : c -> true;

		return SpringFactoriesLoader.forResourceLocation(
				springFactoriesResourceLocation != null ? springFactoriesResourceLocation : SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION,
				classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader()
			)
			.load(targetType)
			.stream()
			.filter(t -> predicateToUse.test(t.getClass()));
	}

}
