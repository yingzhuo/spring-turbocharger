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
package com.github.yingzhuo.turbocharger.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.annotation.Nullable;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://freemarker.apache.org/">Freemarker</a>实现
 *
 * @author 应卓
 * @since 3.4.3
 */
public class StringTemplateRendererImpl implements StringTemplateRenderer, InitializingBean {

	private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);

	@Setter
	private String defaultEncoding = "UTF-8";

	@Setter
	private String[] templateLoaderPaths = new String[]{"classpath:/templates/"};

	@Setter
	private String suffix = ".ftl";

	/**
	 * 默认构造方法
	 */
	public StringTemplateRendererImpl() {
		super();
	}

	@Override
	public String render(String templateName, @Nullable Object data) {
		Assert.hasText(templateName, "template name is required");

		if (data == null) {
			data = new HashMap<String, Object>();
		}

		try (Writer writer = new StringWriter()) {
			var template = cfg.getTemplate(templateName + suffix);
			template.process(data, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (TemplateException e) {
			throw new UncheckedIOException(new IOException(e.getMessage()));
		}
	}

	@Override
	public void afterPropertiesSet() {
		try {
			this.cfg.setTemplateLoader(getTemplateLoader());
			this.cfg.setDefaultEncoding(defaultEncoding);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private MultiTemplateLoader getTemplateLoader() throws IOException {
		Assert.notEmpty(this.templateLoaderPaths, "templateLoaderPaths must not be empty");
		Assert.noNullElements(this.templateLoaderPaths, "templateLoaderPaths must not contain null elements");

		List<TemplateLoader> loaders = new ArrayList<>();
		for (String path : this.templateLoaderPaths) {

			if (path.startsWith("classpath:")) {
				path = path.substring("classpath:".length());

				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				if (!path.endsWith("/")) {
					path += "/";
				}
				var loader = new ClassTemplateLoader(this.getClass(), path);
				loaders.add(loader);
			}

			if (path.startsWith("file:")) {
				path = path.substring("file:".length());
				if (!path.endsWith("/")) {
					path += "/";
				}
				var loader = new FileTemplateLoader(new File(path), true);
				loaders.add(loader);
			}
		}

		return new MultiTemplateLoader(loaders.toArray(new TemplateLoader[0]));
	}

}
