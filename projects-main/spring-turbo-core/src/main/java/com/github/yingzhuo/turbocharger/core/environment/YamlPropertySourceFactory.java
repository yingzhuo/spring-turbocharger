package com.github.yingzhuo.turbocharger.core.environment;

import org.springframework.boot.env.YamlPropertySourceLoader;

/**
 * @author 应卓
 * @since 2.1.3
 */
public class YamlPropertySourceFactory extends AbstractPropertySourceFactory {

	/**
	 * 默认构造方法
	 */
	public YamlPropertySourceFactory() {
		super(new YamlPropertySourceLoader());
	}

}
