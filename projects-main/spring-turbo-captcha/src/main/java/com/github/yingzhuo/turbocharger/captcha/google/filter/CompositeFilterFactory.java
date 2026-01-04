/*
 * Copyright 2022-2026 the original author or authors.
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
package com.github.yingzhuo.turbocharger.captcha.google.filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class CompositeFilterFactory implements FilterFactory {

	private final List<FilterFactory> filterFactories;

	public CompositeFilterFactory(FilterFactory... filterFactories) {
		this.filterFactories = Arrays.asList(filterFactories);
	}

	public CompositeFilterFactory(List<FilterFactory> filterFactories) {
		this.filterFactories = filterFactories;
	}

	public static CompositeFilterFactory of(FilterFactory... filterFactories) {
		return new CompositeFilterFactory(filterFactories);
	}

	public static CompositeFilterFactory of(List<FilterFactory> filterFactories) {
		return new CompositeFilterFactory(filterFactories);
	}

	@Override
	public BufferedImage apply(BufferedImage source) {
		BufferedImage image = source;
		for (FilterFactory factory : filterFactories) {
			image = factory.apply(image);
		}
		return image;
	}

}
