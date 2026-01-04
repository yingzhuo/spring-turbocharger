/*
 * Copyright 2022-2026 the original author or authors.
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
 */
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
