package com.github.yingzhuo.turbocharger.hocon;

import com.github.yingzhuo.turbocharger.core.environment.AbstractPropertySourceFactory;
import com.github.yingzhuo.turbocharger.core.environment.YamlPropertySourceFactory;

public class HoconPropertySourceFactory extends AbstractPropertySourceFactory {

	public HoconPropertySourceFactory() {
		super(new HoconPropertySourceLoader());
	}

}
