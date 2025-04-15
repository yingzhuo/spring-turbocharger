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
package com.github.yingzhuo.turbocharger.redis.aspect;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防重复调用
 *
 * @author 应卓
 * @see AvoidRepeatedInvocationAdvice
 * @since 3.4.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AvoidRepeatedInvocation {

	/**
	 * SpringEL 表达方法调用的唯一性
	 *
	 * @return SpEL
	 */
	public String value();

	/**
	 * 锁自动释放时间
	 *
	 * @return 自动释放时间
	 */
	public long leaseTime() default 5L;

	/**
	 * 锁自动释放时间单位
	 *
	 * @return 自动释放时间单位
	 */
	public TimeUnit leaseTimeUnit() default TimeUnit.SECONDS;

}
