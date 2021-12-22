package com.wl.study.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: swagger2 map参数说明注解
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/23
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonObject {

    ApiJsonProperty[] value(); //对象属性值

    String name();  //对象名称
}