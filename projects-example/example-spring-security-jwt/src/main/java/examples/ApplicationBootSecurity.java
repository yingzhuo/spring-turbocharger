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
package examples;

import com.github.yingzhuo.turbocharger.jwt.autoconfiguration.ImportSM2Algorithm;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.exception.SecurityExceptionHandler;
import com.github.yingzhuo.turbocharger.security.filter.factory.JwtTokenAuthenticationFilterFactoryBean;
import com.github.yingzhuo.turbocharger.security.passwordencoder.EncodingIds;
import com.github.yingzhuo.turbocharger.security.passwordencoder.PasswordEncoderFactories;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true,
	jsr250Enabled = true
)
@ImportSM2Algorithm(
	primary = true,
	publicKeyText = "3059301306072a8648ce3d020106082a811ccf5501822d0342000437aa2b53bcff60b2a27c9cb688092100bddf075f13c717ad490503431ba417b432d304b3fb1192748240089b557da204204e1ce263e84f8d33a77388761ec74a",
	privateKeyText = "308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420f69a150953889cd069e2b7e818226ffb854dcc3e41479a87489049ae0bfa868ca00a06082a811ccf5501822da1440342000437aa2b53bcff60b2a27c9cb688092100bddf075f13c717ad490503431ba417b432d304b3fb1192748240089b557da204204e1ce263e84f8d33a77388761ec74a"
)
public class ApplicationBootSecurity {

	@Bean
	public JwtTokenAuthenticationFilterFactoryBean jwtTokenAuthenticationFilterFactoryBean(
		TokenToUserConverter tokenToUserConverter,
		SecurityExceptionHandler exceptionHandler
	) {
		var factory = new JwtTokenAuthenticationFilterFactoryBean();
		factory.setTokenToUserConverter(tokenToUserConverter);
		factory.setSecurityExceptionHandler(exceptionHandler);
		return factory;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(EncodingIds.bcrypt, EncodingIds.noop);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		final var context = http.getSharedObject(ApplicationContext.class);
		final var exceptionHandler = context.getBean(SecurityExceptionHandler.class);

		http.securityMatcher("/**");

		// enabled
		// default role: 'ROLE_ANONYMOUS'
		http.anonymous(Customizer.withDefaults());

		// enabled
		http.cors(Customizer.withDefaults());

		// stateless
		http.sessionManagement(c ->
			c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		// disabled
		http.csrf(AbstractHttpConfigurer::disable);

		// disabled
		http.httpBasic(AbstractHttpConfigurer::disable);

		// disabled
		http.jee(AbstractHttpConfigurer::disable);

		// disabled
		http.formLogin(AbstractHttpConfigurer::disable);

		// disabled
		http.logout(AbstractHttpConfigurer::disable);

		// disabled
		http.rememberMe(AbstractHttpConfigurer::disable);

		// disabled
		http.requestCache(RequestCacheConfigurer::disable);

		// enabled
		http.headers(Customizer.withDefaults());

		// enable
		http.cors(Customizer.withDefaults());

		// handle errors
		http.exceptionHandling(c ->
			c.authenticationEntryPoint(exceptionHandler)
				.accessDeniedHandler(exceptionHandler)
		);

		// 权限管理
		http.authorizeHttpRequests(c ->
			c.requestMatchers(HttpMethod.POST, "/security/login").permitAll()
				.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
				.requestMatchers(HttpMethod.GET, "/actuator", "/actuator/**").permitAll()
				.requestMatchers("/error").permitAll()
				.anyRequest().hasAnyRole("USER", "ADMIN")
		);

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.debug(true);
	}

}
