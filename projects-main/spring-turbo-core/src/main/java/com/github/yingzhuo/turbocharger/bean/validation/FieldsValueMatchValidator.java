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

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author 应卓
 * @see FieldsValueMatch
 * @since 1.0.0
 */
public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

	@Nullable
	private String field;

	@Nullable
	private String fieldMatch;

	@Override
	public void initialize(FieldsValueMatch annotation) {
		this.field = annotation.field();
		this.fieldMatch = annotation.fieldMatch();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(@Nullable Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		Assert.notNull(field, "field is null");
		Assert.notNull(fieldMatch, "fieldMatch is null");

		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
		return Objects.equals(fieldValue, fieldMatchValue);
	}

}
