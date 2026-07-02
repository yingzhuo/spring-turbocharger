package com.github.yingzhuo.turbocharger.freemarker.autoconfiguration;

import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRenderer;
import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRendererImpl;
import com.github.yingzhuo.turbocharger.freemarker.StringTemplateRendererProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 3.4.3
 */
@AutoConfiguration
@EnableConfigurationProperties(StringTemplateRendererProperties.class)
@ConditionalOnProperty(prefix = "springturbo.stringtemplate", name = "enabled", havingValue = "true", matchIfMissing = true)
public class StringTemplateAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public StringTemplateRenderer stringTemplateRenderer(StringTemplateRendererProperties properties) {
		var bean = new StringTemplateRendererImpl();
		bean.setSuffix(properties.getSuffix());
		bean.setDefaultEncoding(properties.getDefaultEncoding());
		bean.setTemplateLoaderPaths(properties.getTemplateLoaderPaths());
		return bean;
	}

}
