package com.github.yingzhuo.turbocharger.core.environment;

import org.springframework.boot.env.YamlPropertySourceLoader;

public class YamlPropertySourceFactory extends AbstractPropertySourceFactory {

	public YamlPropertySourceFactory() {
		super(new YamlPropertySourceLoader());
	}

}
