package com.github.yingzhuo.turbocharger.jackson.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.turbocharger.jackson.util.JsonUtils;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(name = {
	"com.fasterxml.jackson.databind.ObjectMapper",
	"com.jayway.jsonpath.Configuration"
})
@ConditionalOnBean(ObjectMapper.class)
public class JsonPathAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Configuration jsonPathConfiguration(ObjectMapper om) {
		return Configuration.builder()
			.jsonProvider(new JacksonJsonProvider(om))
			.mappingProvider(new JacksonMappingProvider(om))
			.build();
	}

}
