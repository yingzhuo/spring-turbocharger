/*
 *
 * Copyright 2022-2025 the original author or authors.
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

package com.github.yingzhuo.turbocharger.misc.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.resolver.ClasspathResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Objects;

/**
 * {@link MustacheService} 默认实现类
 *
 * @author 应卓
 * @since 3.3.0
 */
public class MustacheServiceImpl implements MustacheService {

	private final MustacheFactory mustacheFactory;

	/**
	 * 默认构造方法
	 */
	public MustacheServiceImpl() {
		this.mustacheFactory = new DefaultMustacheFactory(new ClasspathResolver());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String render(String classpathTemplateLocation, @Nullable Object data) {
		Assert.hasText(classpathTemplateLocation, "classpathTemplateLocation is requuired");

		if (classpathTemplateLocation.startsWith("classpath:")) {
			classpathTemplateLocation = classpathTemplateLocation.substring("classpath:".length());
		}

		var writer = new StringWriter();
		var mustache = mustacheFactory.compile(classpathTemplateLocation);
		mustache.execute(writer, Objects.requireNonNullElseGet(data, HashMap::new));
		return writer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String render(String templateString, String templateName, @Nullable Object data) {
		Assert.hasText(templateName, "templateName is required");

		var writer = new StringWriter();
		var mustache = mustacheFactory.compile(new StringReader(templateString), templateName);
		mustache.execute(writer, Objects.requireNonNullElseGet(data, HashMap::new));
		return writer.toString();
	}

}
