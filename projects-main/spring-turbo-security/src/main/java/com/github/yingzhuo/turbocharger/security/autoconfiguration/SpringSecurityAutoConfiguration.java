package com.github.yingzhuo.turbocharger.security.autoconfiguration;

import com.github.yingzhuo.turbocharger.security.exception.SecurityExceptionHandler;
import com.github.yingzhuo.turbocharger.security.exception.SecurityExceptionHandlerImpl;
import com.github.yingzhuo.turbocharger.security.misc.GrantedAuthorityConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SpringSecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public HttpFirewall httpFirewall() {
		var bean = new StrictHttpFirewall();
		bean.setAllowSemicolon(true);
		return bean;
	}

	@Bean
	@ConditionalOnMissingBean
	public SecurityExceptionHandler securityExceptionHandler() {
		return new SecurityExceptionHandlerImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public GrantedAuthorityConverter grantedAuthorityConverter() {
		return new GrantedAuthorityConverter();
	}

}
