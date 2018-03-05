package com.liyang.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
//将wdsjsh 中有关person的字段保存在person中
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface PersonField {
	String name() default "";
}
