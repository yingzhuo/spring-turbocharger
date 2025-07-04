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
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;

import java.lang.annotation.*;

/**
 * 获取当前用户的{@link org.springframework.security.core.Authentication}对象
 *
 * @author 应卓
 * @see SecurityContext#getAuthentication()
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@CurrentSecurityContext(expression = "authentication")
public @interface CurrentAuthentication {

	@AliasFor(annotation = CurrentSecurityContext.class, attribute = "errorOnInvalidType")
	boolean errorOnInvalidType() default false;

}
