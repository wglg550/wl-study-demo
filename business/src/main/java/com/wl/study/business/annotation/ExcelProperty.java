package com.wl.study.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {
	/**
	 * 导出时列名
	 */
    String name() default "";
    
	/**
	 * 导出时列宽
	 */
    int width() default 0;
	/**
	 * 排序
	 */
    int index();
    /**
     * 类型
     * 0:导入(读excel)
     * 1:导出(写excel)
     * 2:导入&导出
     */
    int type() default 2;
}
