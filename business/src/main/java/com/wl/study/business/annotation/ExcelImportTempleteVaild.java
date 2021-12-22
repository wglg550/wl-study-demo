package com.wl.study.business.annotation;

import java.lang.annotation.*;

/**
 * @Description: excel模版校验注释
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/7/20
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImportTempleteVaild {

    boolean value() default false;
}
