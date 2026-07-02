package com.github.yingzhuo.turbocharger.jackson.util;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.turbocharger.util.spi.SPILoader;
import org.jspecify.annotations.Nullable;
import org.springframework.util.ClassUtils;

import java.util.function.Predicate;

/**
 * Jackson模块加载工具
 *
 * @author 应卓
 * @see Module
 * @see <a href="https://github.com/FasterXML/jackson">Jackson官方文档</a>
 * @since 3.3.1
 */
public final class JacksonModuleUtils {

	/**
	 * 私有构造方法
	 */
	private JacksonModuleUtils() {
		super();
	}

	public static void loadModulesAndRegister(@Nullable ObjectMapper mapper) {
		loadModulesAndRegister(mapper, null);
	}

	public static void loadModulesAndRegister(@Nullable ObjectMapper mapper, @Nullable Predicate<Class<?>> predicate) {
		if (mapper != null) {
			SPILoader.builder(Module.class, ClassUtils.getDefaultClassLoader())
				.withJdkServiceLoader()
				.withSpringFactories()
				.filter(predicate != null ? predicate : c -> true)
				.build()
				.load()
				.forEach(mapper::registerModule);
		}
	}

}
