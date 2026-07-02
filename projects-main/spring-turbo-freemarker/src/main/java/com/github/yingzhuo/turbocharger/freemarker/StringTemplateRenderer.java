package com.github.yingzhuo.turbocharger.freemarker;

import org.jspecify.annotations.Nullable;

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
