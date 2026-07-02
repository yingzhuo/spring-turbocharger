package com.github.yingzhuo.turbocharger.jackson.autoconfiguration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.turbocharger.util.spi.SPILoader;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
public class JacksonModuleAutoConfiguration {

	@Autowired(required = false)
	private void initModules(@Nullable ObjectMapper mapper) {
		if (mapper != null) {
			SPILoader.getDefault(Module.class)
				.load()
				.forEach(mapper::registerModule);
		}
	}

}
