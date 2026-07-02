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

@Aspect
public class AvoidRepeatedInvocationAdvice implements Ordered {

	private final RedisOperations<String, String> redisOperations;
	private final RuntimeExceptionSupplier exceptionSupplier;
	private final int order;

	public AvoidRepeatedInvocationAdvice(RedisOperations<String, String> redisOperations, RuntimeExceptionSupplier exceptionSupplier) {
		this(redisOperations, exceptionSupplier, HIGHEST_PRECEDENCE);
	}

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
