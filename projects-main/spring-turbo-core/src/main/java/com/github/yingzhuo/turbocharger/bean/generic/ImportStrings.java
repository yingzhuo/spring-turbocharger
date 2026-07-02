package com.github.yingzhuo.turbocharger.bean.generic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportStringsCfg.class)
public @interface ImportStrings {

	ImportString[] value();

}
