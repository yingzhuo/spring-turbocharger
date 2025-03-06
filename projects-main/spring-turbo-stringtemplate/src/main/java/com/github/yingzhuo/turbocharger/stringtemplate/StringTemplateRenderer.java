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

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 文本模板渲染器
 *
 * @author 应卓
 * @since 3.4.3
 */
public interface StringTemplateRenderer {

	/**
	 * 渲染文本
	 *
	 * @param classpathTemplateLocation 模板在classpath中的位置
	 * @param data                      数据
	 * @return 渲染结果
	 */
	public String render(String classpathTemplateLocation, @Nullable Object data);

	/**
	 * 渲染文本
	 *
	 * @param template     模板
	 * @param templateName 模板名称
	 * @param data         数据
	 * @return 渲染结果
	 */
	public default String render(Resource template, String templateName, @Nullable Object data) {
		try {
			return render(template.getContentAsString(UTF_8), templateName, data);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * 渲染文本
	 *
	 * @param templateString 字符串模版
	 * @param templateName   模板名称
	 * @param data           数据
	 * @return 渲染结果
	 */
	public String render(String templateString, String templateName, @Nullable Object data);
}
