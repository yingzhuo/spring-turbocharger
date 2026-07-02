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
