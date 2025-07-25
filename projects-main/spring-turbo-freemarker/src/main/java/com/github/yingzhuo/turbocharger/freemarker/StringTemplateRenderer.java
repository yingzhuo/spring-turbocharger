/*
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
 */
package com.github.yingzhuo.turbocharger.freemarker;

import org.springframework.lang.Nullable;

/**
 * 文本模板渲染器
 *
 * @author 应卓
 * @since 3.4.3
 */
@FunctionalInterface
public interface StringTemplateRenderer {

	/**
	 * 渲染文本
	 *
	 * @param templateName 模板名称
	 * @return 渲染结果
	 */
	public default String render(String templateName) {
		return render(templateName, null);
	}

	/**
	 * 渲染文本
	 *
	 * @param templateName 模板名称
	 * @param data         数据
	 * @return 渲染结果
	 */
	public String render(String templateName, @Nullable Object data);

}
