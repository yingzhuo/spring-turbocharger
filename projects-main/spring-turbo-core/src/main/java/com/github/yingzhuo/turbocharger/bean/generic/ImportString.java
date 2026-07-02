package com.github.yingzhuo.turbocharger.bean.generic;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see ImportStringsCfg
 * @since 3.5.3
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Import(ImportStringsCfg.class)
@Repeatable(ImportStrings.class)
public @interface ImportString {

	/**
	 * Bean的名称
	 *
	 * @return Bean的名称
	 */
	String beanName() default "";

	/**
	 * Bean的Primary属性
	 *
	 * @return Bean的Primary属性
	 */
	boolean primary() default false;

	/**
	 * 资源位置
	 *
	 * @return 资源位置
	 */
	@AliasFor(attribute = "location")
	String value() default "";

	/**
	 * 资源位置
	 *
	 * @return 资源位置
	 */
	@AliasFor(attribute = "value")
	String location() default "";

	/**
	 * 编码
	 *
	 * @return 编码
	 */
	String charset() default "UTF-8";

	/**
	 * 是否需要trim
	 *
	 * @return 是否需要trim
	 */
	boolean trim() default false;

	/**
	 * 是否需要trim每一行
	 *
	 * @return 是否需要trim每一行
	 */
	boolean trimEachLine() default false;

}
