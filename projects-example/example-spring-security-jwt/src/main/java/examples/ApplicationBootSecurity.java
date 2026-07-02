package examples;

import com.github.yingzhuo.turbocharger.jwt.algorithm.GenericAlgorithmFactoryBean;
import com.github.yingzhuo.turbocharger.security.authentication.TokenToUserConverter;
import com.github.yingzhuo.turbocharger.security.exception.SecurityExceptionHandler;
import com.github.yingzhuo.turbocharger.security.filter.TokenAuthenticationFilter;
import com.github.yingzhuo.turbocharger.security.passwordencoder.PasswordEncoderFactoryBean;
import com.github.yingzhuo.turbocharger.security.token.resolver.BearerTokenResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true,
	jsr250Enabled = true
)
public class ApplicationBootSecurity {

	@Bean
	public GenericAlgorithmFactoryBean genericAlgorithmFactoryBean() {
		var factory = new GenericAlgorithmFactoryBean();
		factory.setPemLocation("classpath:jwt-ecdsa.pem");
		factory.setPassword("123456");
		return factory;
	}

	@Bean
	public PasswordEncoderFactoryBean passwordEncoderFactoryBean() {
		return new PasswordEncoderFactoryBean();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		final var context = http.getSharedObject(ApplicationContext.class);
		final var exceptionHandler = context.getBean(SecurityExceptionHandler.class);

