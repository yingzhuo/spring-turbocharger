package com.github.yingzhuo.turbocharger.redis.aspect;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AvoidRepeatedInvocation {

	public String value();

	public long leaseTime() default 5L;

	public TimeUnit leaseTimeUnit() default TimeUnit.SECONDS;

}
