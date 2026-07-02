package com.github.yingzhuo.turbocharger.core.environment;

import org.jspecify.annotations.Nullable;
import org.springframework.core.env.PropertySource;

public final class EmptyPropertySource extends PropertySource<Object> {

	public EmptyPropertySource(String name) {
		super(name);
	}

	@Nullable
	@Override
	public Object getProperty(String name) {
		return null;
	}

}
