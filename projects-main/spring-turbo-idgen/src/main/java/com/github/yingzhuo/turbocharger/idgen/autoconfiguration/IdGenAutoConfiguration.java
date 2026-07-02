package com.github.yingzhuo.turbocharger.idgen.autoconfiguration;

import com.github.yingzhuo.turbocharger.idgen.TSIDGenerator;
import com.github.yingzhuo.turbocharger.idgen.UUIDGenerator;
import com.github.yingzhuo.turbocharger.idgen.impl.TSIDGeneratorImpl;
import com.github.yingzhuo.turbocharger.idgen.impl.UUIDGeneratorImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class IdGenAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UUIDGenerator uuidGenerator() {
		return new UUIDGeneratorImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public TSIDGenerator tsidGenerator() {
		return new TSIDGeneratorImpl();
	}

}
