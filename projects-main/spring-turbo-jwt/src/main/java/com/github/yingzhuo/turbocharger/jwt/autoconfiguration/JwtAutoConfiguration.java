package com.github.yingzhuo.turbocharger.jwt.autoconfiguration;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.jwt.JwtServiceImpl;
import com.github.yingzhuo.turbocharger.jwt.VerificationCustomizer;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置类
 *
 * @author 应卓
 * @since 3.5.0
 */
@AutoConfiguration
@ConditionalOnBean(Algorithm.class)
@ConditionalOnMissingBean(JwtService.class)
public class JwtAutoConfiguration {

	@Bean
	public JwtService jsonWebTokenService(Algorithm algorithm, @Autowired(required = false) @Nullable VerificationCustomizer verificationCustomizer) {
		return new JwtServiceImpl(algorithm, verificationCustomizer);
	}

}
