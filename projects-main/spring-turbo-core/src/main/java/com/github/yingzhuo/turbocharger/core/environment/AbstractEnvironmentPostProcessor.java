/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.core.environment;

import org.apache.commons.logging.Log;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 3.5.0
 */
public abstract class AbstractEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	protected final Log log;
	protected final int order;

	protected AbstractEnvironmentPostProcessor(DeferredLogFactory logFactory) {
		this(logFactory, 0);
	}

	protected AbstractEnvironmentPostProcessor(DeferredLogFactory logFactory, int order) {
		this.log = logFactory.getLog(getClass());
		this.order = order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOrder() {
		return 0;
	}

}
