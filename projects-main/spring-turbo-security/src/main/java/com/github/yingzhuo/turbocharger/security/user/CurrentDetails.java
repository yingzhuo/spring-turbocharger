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
package com.github.yingzhuo.turbocharger.security.user;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.*;

/**
 * 获取当前用户的Details对象
 *
 * @author 应卓
 * @see org.springframework.security.core.context.SecurityContext
 * @see AbstractAuthenticationToken#getDetails()
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@CurrentSecurityContext(expression = "authentication.details")
public @interface CurrentDetails {

	@AliasFor(annotation = CurrentSecurityContext.class, attribute = "errorOnInvalidType")
	boolean errorOnInvalidType() default false;

}
