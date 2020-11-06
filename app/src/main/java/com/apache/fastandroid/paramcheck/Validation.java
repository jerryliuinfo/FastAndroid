package com.apache.fastandroid.paramcheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: jerry
 * created on: 2020/9/16 11:59 AM
 * description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    ValidateType value() default ValidateType.TYPE_STRING;
}
