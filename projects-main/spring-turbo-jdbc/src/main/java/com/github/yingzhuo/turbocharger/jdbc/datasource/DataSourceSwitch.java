package com.github.yingzhuo.turbocharger.jdbc.datasource;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSourceSwitch {

	public String value();

}
