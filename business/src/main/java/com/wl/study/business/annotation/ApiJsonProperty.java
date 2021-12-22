package com.wl.study.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: swagger2 map参数说明属性注解
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/23
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonProperty {

    String key();  //key

    String example() default "";

    String type() default "string";

    String description() default "";

    boolean required() default false;
}