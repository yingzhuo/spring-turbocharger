package com.github.yingzhuo.turbocharger.hocon;

import com.github.yingzhuo.turbocharger.core.environment.AbstractPropertySourceFactory;
import com.github.yingzhuo.turbocharger.core.environment.YamlPropertySourceFactory;

/**
 * @author 应卓
 * @see YamlPropertySourceFactory
 * @see org.springframework.context.annotation.PropertySource
 * @see org.springframework.context.annotation.PropertySources
 * @since 2.1.3
 */
public class HoconPropertySourceFactory extends AbstractPropertySourceFactory {

	/**
	 * 默认构造方法
	 */
	public HoconPropertySourceFactory() {
		super(new HoconPropertySourceLoader());
	}

}
