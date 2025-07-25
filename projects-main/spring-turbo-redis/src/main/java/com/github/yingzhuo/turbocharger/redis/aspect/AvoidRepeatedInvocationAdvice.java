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
package com.github.yingzhuo.turbocharger.redis.aspect;

import com.github.yingzhuo.turbocharger.aspect.AspectSpELTemplate;
import com.github.yingzhuo.turbocharger.core.AspectUtils;
import com.github.yingzhuo.turbocharger.exception.RuntimeExceptionSupplier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see AvoidRepeatedInvocation
 * @since 3.4.0
 */
@Aspect
public class AvoidRepeatedInvocationAdvice implements Ordered {

	private final RedisOperations<String, String> redisOperations;
	private final RuntimeExceptionSupplier exceptionSupplier;
	private final int order;

	/**
	 * 构造方法
	 *
	 * @param redisOperations   RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param exceptionSupplier 异常提供器，如果判断为重复调用。使用这个东西产生异常并抛出
	 */
	public AvoidRepeatedInvocationAdvice(RedisOperations<String, String> redisOperations, RuntimeExceptionSupplier exceptionSupplier) {
		this(redisOperations, exceptionSupplier, HIGHEST_PRECEDENCE);
	}

	/**
	 * 构造方法
	 *
	 * @param redisOperations   RedisOperations实例，通常是 {@link StringRedisTemplate}
	 * @param exceptionSupplier 异常提供器，如果判断为重复调用。使用这个东西产生异常并抛出
	 * @param order             切面排序
	 */
	public AvoidRepeatedInvocationAdvice(RedisOperations<String, String> redisOperations, RuntimeExceptionSupplier exceptionSupplier, int order) {
		Assert.notNull(redisOperations, "redisOperations is required");
		Assert.notNull(exceptionSupplier, "exceptionSupplier is required");

		this.redisOperations = redisOperations;
		this.exceptionSupplier = exceptionSupplier;
		this.order = order;
	}

	@Around("@annotation(com.github.yingzhuo.turbocharger.redis.aspect.AvoidRepeatedInvocation)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		var annotation = AspectUtils.getMethodAnnotation(joinPoint, AvoidRepeatedInvocation.class);

		if (annotation == null) {
			annotation = AspectUtils.getObjectTypeAnnotation(joinPoint, AvoidRepeatedInvocation.class);
		}

		if (annotation == null) {
			return joinPoint.proceed();
		}

		var redisKey = AspectSpELTemplate.newInstance(annotation.value(), joinPoint)
			.setRootObject(null)
			.getValue();

		var success = redisOperations.opsForValue()
			.setIfAbsent(redisKey, "1", annotation.leaseTime(), annotation.leaseTimeUnit());

		if (Boolean.TRUE.equals(success)) {
			return joinPoint.proceed();
		} else {
			throw exceptionSupplier.get();
		}
	}

	@Override
	public int getOrder() {
		return this.order;
	}

}
