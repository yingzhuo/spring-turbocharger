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
package com.github.yingzhuo.turbocharger.bean.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author 应卓
 * @see FieldsValueMatchValidator
 * @since 1.0.0
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Repeatable(FieldsValueMatch.Container.class)
public @interface FieldsValueMatch {

	public String message() default "";

	public String field();

	public String fieldMatch();

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};

	// -----------------------------------------------------------------------------------------------------------------

	@Documented
	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface Container {
		public FieldsValueMatch[] value();
	}

}
