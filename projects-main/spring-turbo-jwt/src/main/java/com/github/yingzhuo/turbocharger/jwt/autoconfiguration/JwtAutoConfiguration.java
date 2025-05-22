/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.jwt.autoconfiguration;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.turbocharger.jwt.JwtServiceImpl;
import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.jwt.VerificationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

/**
 * 自动配置类
 *
 * @author 应卓
 * @since 3.5.0
 */
@ConditionalOnBean(Algorithm.class)
@ConditionalOnMissingBean(JwtService.class)
public class JwtAutoConfiguration {

	@Bean
	public JwtService jsonWebTokenService(Algorithm algorithm, @Autowired(required = false) @Nullable VerificationCustomizer verificationCustomizer) {
		return new JwtServiceImpl(algorithm, verificationCustomizer);
	}

}
