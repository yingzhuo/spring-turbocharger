package com.github.yingzhuo.turbocharger.core.environment;

import org.jspecify.annotations.Nullable;
import org.springframework.core.env.PropertySource;

/**
 * @author 应卓
 * @since 2.1.3
 */
public final class EmptyPropertySource extends PropertySource<Object> {

	/**
	 * 构造方法
	 *
	 * @param name 名称
	 */
	public EmptyPropertySource(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Object getProperty(String name) {
		return null;
	}

}
