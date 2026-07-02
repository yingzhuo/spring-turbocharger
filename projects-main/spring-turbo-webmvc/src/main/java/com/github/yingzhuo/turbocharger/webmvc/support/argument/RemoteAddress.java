package com.github.yingzhuo.turbocharger.webmvc.support.argument;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteAddress {
}
