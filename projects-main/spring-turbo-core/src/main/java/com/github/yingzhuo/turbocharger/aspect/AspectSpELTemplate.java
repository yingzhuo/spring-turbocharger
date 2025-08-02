/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.aspect;

import com.github.yingzhuo.turbocharger.core.SpELUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Aspect环绕式切面相关小工具
 *
 * @param <T> SpEL结果类型
 * @author 应卓
 * @see SpELUtils
 * @since 3.4.0
 */
public final class AspectSpELTemplate<T> implements Serializable {

	/**
	 * SpringExpression
	 */
	private final String expression;

	/**
	 * SpringExpression 变量
	 */
	private final Map<String, Object> expressionVariables;

	@Nullable
	private Object rootObject;

	private AspectSpELTemplate(String expression, ProceedingJoinPoint joinPoint) {
		Assert.hasText(expression, "expression is required");
		Assert.notNull(joinPoint, "joinPoint is required");
		this.expression = expression;

		var map = new HashMap<String, Object>();
		map.put("args", joinPoint.getArgs());
		map.put("argsLength", joinPoint.getArgs().length);
		map.put("method", ((MethodSignature) joinPoint.getSignature()).getMethod());
		map.put("methodName", ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
		map.put("target", joinPoint.getTarget());
		map.put("targetType", joinPoint.getTarget().getClass().getName());

		this.expressionVariables = Collections.unmodifiableMap(map);
	}

	public static AspectSpELTemplate<String> newInstance(String expression, ProceedingJoinPoint joinPoint) {
		return newInstance(expression, joinPoint, String.class);
	}

	public static <R> AspectSpELTemplate<R> newInstance(String expression, ProceedingJoinPoint joinPoint, Class<R> returnType) {
		Assert.notNull(returnType, "returnType is required");
		return new AspectSpELTemplate<>(expression, joinPoint);
	}

	public AspectSpELTemplate<T> setRootObject(@Nullable Object rootObject) {
		this.rootObject = rootObject;
		return this;
	}

	public T getValue() {
		return Objects.requireNonNull(SpELUtils.getValue(expression, rootObject, expressionVariables));
	}

	public Map<String, Object> getExpressionVariables() {
		return Collections.unmodifiableMap(expressionVariables);
	}

}
