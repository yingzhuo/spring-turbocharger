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
package com.github.yingzhuo.turbocharger.jdbc.datasource;

import com.github.yingzhuo.turbocharger.core.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @see DataSourceSwitch
 * @since 3.4.1
 */
@Aspect
public class DataSourceSwitchAdvice implements Ordered {

	private final int order;

	public DataSourceSwitchAdvice() {
		this(HIGHEST_PRECEDENCE);
	}

	public DataSourceSwitchAdvice(int order) {
		this.order = order;
	}

	@Around("@annotation(com.github.yingzhuo.turbocharger.jdbc.datasource.DataSourceSwitch)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		var annotation = AspectUtils.getMethodAnnotation(joinPoint, DataSourceSwitch.class);

		if (annotation == null) {
			annotation = AspectUtils.getObjectTypeAnnotation(joinPoint, DataSourceSwitch.class);
		}

		if (annotation == null) {
			return joinPoint.proceed();
		}

		try {
			RoutingDataSourceLookup.set(annotation.value());
			return joinPoint.proceed();
		} finally {
			RoutingDataSourceLookup.remove();
		}
	}

	@Override
	public int getOrder() {
		return order;
	}

}
