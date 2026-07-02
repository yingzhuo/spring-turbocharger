package com.github.yingzhuo.turbocharger.freemarker;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.collection.ArrayUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 3.4.3
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "springturbo.stringtemplate")
public class StringTemplateRendererProperties implements InitializingBean, Serializable {

	private boolean enabled = true;
	private String defaultEncoding = "UTF-8";
	private String[] templateLoaderPaths = new String[]{"classpath:/templates/"};
	private String suffix = ".ftl";

	@Override
	public void afterPropertiesSet() {
		if (StringUtils.isBlank(suffix)) {
			suffix = "";
		}
		if (StringUtils.isBlank(defaultEncoding)) {
			defaultEncoding = "UTF-8";
		}
		if (ArrayUtils.isNullOrEmpty(templateLoaderPaths)) {
			templateLoaderPaths = new String[]{"classpath:/templates/"};
		}
	}

}
