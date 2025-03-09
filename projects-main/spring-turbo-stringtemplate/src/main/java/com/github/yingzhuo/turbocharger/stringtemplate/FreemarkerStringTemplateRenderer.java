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
package com.github.yingzhuo.turbocharger.stringtemplate;

import com.github.yingzhuo.turbocharger.util.io.IOExceptionUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.annotation.Nullable;
import lombok.Setter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class FreemarkerStringTemplateRenderer implements StringTemplateRenderer {

	private final Configuration cfg;

	@Setter
	private String defaultEncoding = "UTF-8";

	@Setter
	private String templateLoaderPath = "templates/";

	@Setter
	private String suffix = ".ftl";

	public FreemarkerStringTemplateRenderer() {
		cfg = new Configuration(Configuration.VERSION_2_3_34);
		cfg.setClassForTemplateLoading(FreemarkerStringTemplateRenderer.class, this.templateLoaderPath);
		cfg.setDefaultEncoding(defaultEncoding);
	}

	@Override
	public String render(String templateName, @Nullable Object data) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}

		try (Writer writer = new StringWriter()) {
			var template = cfg.getTemplate(templateName + suffix);
			template.process(data, writer);
			return writer.toString();
		} catch (IOException e) {
			throw IOExceptionUtils.toUnchecked(e);
		} catch (TemplateException e) {
			throw IOExceptionUtils.toUnchecked(e.getMessage());
		}
	}

}
