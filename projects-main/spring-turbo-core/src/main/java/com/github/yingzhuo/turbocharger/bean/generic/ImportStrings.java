package com.github.yingzhuo.turbocharger.bean.generic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see ImportString
 * @see Repeatable
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportStringsCfg.class)
public @interface ImportStrings {

	/**
	 * {@link ImportString} 实例 (多个)
	 *
	 * @return {@link ImportString} 实例
	 */
	ImportString[] value();

}
