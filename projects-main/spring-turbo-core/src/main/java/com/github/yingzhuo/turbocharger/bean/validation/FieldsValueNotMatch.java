package com.github.yingzhuo.turbocharger.bean.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsValueNotMatchValidator.class)
@Repeatable(FieldsValueNotMatch.Container.class)
public @interface FieldsValueNotMatch {

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
		public FieldsValueNotMatch[] value();
	}

}
