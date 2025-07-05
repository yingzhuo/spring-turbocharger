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
package com.github.yingzhuo.turbocharger.core;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import static com.github.yingzhuo.turbocharger.core.SpringUtils.getApplicationEventPublisher;

/**
 * {@link ApplicationEventPublisher}相关工具
 *
 * @author 应卓
 * @see SpringUtils
 * @since 3.3.2
 */
@Deprecated(forRemoval = true, since = "3.5.3")
public final class ApplicationEventPublisherUtils {

	/**
	 * 私有构造方法
	 */
	private ApplicationEventPublisherUtils() {
		super();
	}

	/**
	 * 发布事件
	 *
	 * @param event 事件实例
	 */
	public static void publish(ApplicationEvent event) {
		Assert.notNull(event, "event is required");
		getApplicationEventPublisher().publishEvent(event);
	}

	/**
	 * 发布事件
	 *
	 * @param event 事件实例
	 * @since 3.3.1
	 */
	public static void publish(Object event) {
		Assert.notNull(event, "event is required");
		getApplicationEventPublisher().publishEvent(event);
	}

}
