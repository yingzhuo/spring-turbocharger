package com.github.yingzhuo.turbocharger.bean.generic;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportStringsCfg.class)
@Repeatable(ImportStrings.class)
public @interface ImportString {

	String beanName() default "";

	boolean primary() default false;

	@AliasFor(attribute = "location")
	String value() default "";

	@AliasFor(attribute = "value")
	String location() default "";

	String charset() default "UTF-8";

	boolean trim() default false;

	boolean trimEachLine() default false;

}
