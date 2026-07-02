package com.github.yingzhuo.turbocharger.freemarker;

import org.jspecify.annotations.Nullable;

@FunctionalInterface
public interface StringTemplateRenderer {

	public default String render(String templateName) {
		return render(templateName, null);
	}

	public String render(String templateName, @Nullable Object data);

}
