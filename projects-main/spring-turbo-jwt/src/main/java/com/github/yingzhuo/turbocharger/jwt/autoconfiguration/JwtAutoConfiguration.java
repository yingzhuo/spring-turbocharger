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

import com.github.yingzhuo.turbocharger.jwt.JwtService;
import com.github.yingzhuo.turbocharger.jwt.JwtServiceImpl;
import com.github.yingzhuo.turbocharger.jwt.alg.JwtSigner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置类
 *
 * @author 应卓
 * @see com.github.yingzhuo.turbocharger.jwt.alg.KeyPairPemJwtSignerFactoryBean
 * @see com.github.yingzhuo.turbocharger.jwt.alg.KeyPairStoreJwtSignerFactoryBean
 * @since 3.3.2
 */
@ConditionalOnBean(JwtSigner.class)
@ConditionalOnMissingBean(JwtService.class)
public class JwtAutoConfiguration {

	@Bean
	public JwtService jsonWebTokenService(JwtSigner jwtSigner) {
		return new JwtServiceImpl(jwtSigner);
	}

}
