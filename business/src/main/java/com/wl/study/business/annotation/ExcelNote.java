package com.wl.study.business.annotation;

import java.lang.annotation.*;

/**
* @Description: excel导出注释
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020/7/20
*/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelNote {

    String value() default "";
}
