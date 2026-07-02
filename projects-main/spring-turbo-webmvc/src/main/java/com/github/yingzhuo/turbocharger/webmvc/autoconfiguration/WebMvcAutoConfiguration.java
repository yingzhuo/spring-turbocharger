package com.github.yingzhuo.turbocharger.webmvc.autoconfiguration;

import com.github.yingzhuo.turbocharger.webmvc.SpringTurboWebMvcProperties;
import com.github.yingzhuo.turbocharger.webmvc.databinding.DataBinderInitializingAdvice;
import com.github.yingzhuo.turbocharger.webmvc.support.argument.RemoteAddressHandlerMethodArgumentResolver;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(SpringTurboWebMvcProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

	@Autowired(required = false)
	public void configBeanNameViewResolver(@Nullable BeanNameViewResolver resolver) {
		if (resolver != null) {
			resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
		}
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RemoteAddressHandlerMethodArgumentResolver());
	}

	@Bean
	@ConditionalOnProperty(prefix = "springturbo.webmvc", name = "data-binder-initializing-advice", havingValue = "true", matchIfMissing = true)
	public DataBinderInitializingAdvice dataBinderInitializingAdvice() {
		return new DataBinderInitializingAdvice();
	}

}
