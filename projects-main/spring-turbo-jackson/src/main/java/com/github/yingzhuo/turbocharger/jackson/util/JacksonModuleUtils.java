package com.github.yingzhuo.turbocharger.jackson.util;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.turbocharger.util.spi.SPILoader;
import org.jspecify.annotations.Nullable;
import org.springframework.util.ClassUtils;

import java.util.function.Predicate;

public final class JacksonModuleUtils {

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
