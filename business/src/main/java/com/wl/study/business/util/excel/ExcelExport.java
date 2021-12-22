package com.wl.study.business.util.excel;

import com.wl.study.business.annotation.ExcelProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ExcelExport {

    private Class<?> dataClass;
    private List<ColumnSetting> columnSettings;

    public ExcelExport(){

    }

    public ExcelExport(Class<?> dataClass) {
        this.dataClass = dataClass;
        this.columnSettings = getColumnSettings(dataClass);
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
    }

    public List<ColumnSetting> getColumnSettings() {
        return columnSettings;
    }

    public void setColumnSettings(List<ColumnSetting> columnSettings) {
        this.columnSettings = columnSettings;
    }

    /**
     * 提取ExcelProperty注解类的字段信息
     *
     * @param dataClass 需要解析 写入excel的数据类型
     * @return
     */
    protected List<ColumnSetting> getColumnSettings(Class<?> dataClass) {
        List<ColumnSetting> columnSettings = new ArrayList<>();
        // 先在方法上找ExcelProperty注解
        Method[] methods = dataClass.getDeclaredMethods();
        for (Method method : methods) {
            ExcelProperty exportProperty = method.getAnnotation(ExcelProperty.class);
            if (exportProperty != null && exportProperty.name().trim().length() > 0) {
                ColumnSetting columnSetting = new ColumnSetting(exportProperty.name(), method.getName(),
                        exportProperty.width(), exportProperty.index());
                columnSettings.add(columnSetting);
            }
        }
        // 如果方法上找不到注解,再到属性上找
        if (columnSettings.size() == 0) {
            Field[] fields = dataClass.getDeclaredFields();
            for (Field field : fields) {
                ExcelProperty exportProperty = field.getAnnotation(ExcelProperty.class);
                if (exportProperty != null && exportProperty.name().trim().length() > 0) {
                    ColumnSetting columnSetting = new ColumnSetting(exportProperty.name(),
                            "get" + toUpperCaseFirstOne(field.getName()), exportProperty.width(),
                            exportProperty.index());
                    columnSettings.add(columnSetting);
                }
            }
        }
        Collections.sort(columnSettings);
        return columnSettings;
    }

    private static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
