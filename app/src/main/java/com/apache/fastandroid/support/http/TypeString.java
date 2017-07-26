package com.apache.fastandroid.support.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author REXZOU
 * @Description:
 * @date 2016/2/26 10:32
 * @copyright TCL-MIG
 */

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface TypeString {
}
