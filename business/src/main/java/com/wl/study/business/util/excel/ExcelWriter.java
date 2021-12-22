package com.wl.study.business.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ExcelWriter extends ExcelExport {

    private XSSFWorkbook workbook;// 工作簿

    private Sheet sheet; // 工作表

    private Row row = null;// 创建一行

    private Cell cell = null;

    private XSSFCellStyle style;

    private XSSFCellStyle style2;

    public ExcelWriter() {
        workbook = new XSSFWorkbook();
    }

    public ExcelWriter(Class<?> dataClass, String sheetName) {
        super(dataClass);
        // 声明一个工作薄
        // workbook = new SXSSFWorkbook(100);//使用该方法会有权限问题
        workbook = new XSSFWorkbook();
        // 生成一个表格
        sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
    }

    public Sheet createSheet(Class<?> dataClass, String sheetName) {
        setDataClass(dataClass);
        setColumnSettings(getColumnSettings(dataClass));
        Sheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        return sheet;
    }

    private List<ColumnSetting> createColumnSettings() {
        List<ColumnSetting> columnSettings = this.getColumnSettings();
        // 产生表格标题行
        row = sheet.createRow(0);
        for (short i = 0; i < columnSettings.size(); i++) {
            cell = row.createCell(i);
            style = workbook.createCellStyle();
            style.setFillForegroundColor(new XSSFColor(new Color(227, 239, 217)));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(columnSettings.get(i).getHeader());
            cell.setCellValue(text);
            if (columnSettings.get(i).getWidth() > 0) {
                sheet.setColumnWidth(i, columnSettings.get(i).getWidth() * 256);
            }
        }
        return columnSettings;
    }

    /**
     * 写入excel
     *
     * @param dataset 数据集合
     * @param out     输出流
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void write(Collection<?> dataset, OutputStream out) throws Exception {
        List<ColumnSetting> columnSettings = this.createColumnSettings();
        int index = 0;
        if (Objects.nonNull(dataset) && dataset.size() > 0) {
            for (Object obj : dataset) {
                index++;
                row = sheet.createRow(index);// 创建行
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                for (short i = 0; i < columnSettings.size(); i++) {
                    cell = row.createCell(i);// 创建列
                    cell.setCellStyle(style2);
                    String methodName = columnSettings.get(i).getGetMethodName();
                    Method method = this.getDataClass().getMethod(methodName, new Class[]{});
                    Object value = method.invoke(obj, new Object[]{});
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
        }
        workbook.write(out);
    }

    /**
     * 写入excel
     *
     * @param dataset 数据集合
     * @param sheet
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void write(Collection<?> dataset, Sheet sheet) throws Exception {
        List<ColumnSetting> columnSettings = this.createColumnSettings(sheet);
        int index = 0;
        if (Objects.nonNull(dataset) && dataset.size() > 0) {
            for (Object obj : dataset) {
                index++;
                row = sheet.createRow(index);// 创建行
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                for (short i = 0; i < columnSettings.size(); i++) {
                    cell = row.createCell(i);// 创建列
                    cell.setCellStyle(style2);
                    String methodName = columnSettings.get(i).getGetMethodName();
                    Method method = this.getDataClass().getMethod(methodName, new Class[]{});
                    Object value = method.invoke(obj, new Object[]{});
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
        }
//        workbook.write(out);
    }

    private List<ColumnSetting> createColumnSettings(Sheet sheet) {
        List<ColumnSetting> columnSettings = this.getColumnSettings();
        // 产生表格标题行
        row = sheet.createRow(0);
        for (short i = 0; i < columnSettings.size(); i++) {
            cell = row.createCell(i);
            style = workbook.createCellStyle();
            style.setFillForegroundColor(new XSSFColor(new Color(227, 239, 217)));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(columnSettings.get(i).getHeader());
            cell.setCellValue(text);
            if (columnSettings.get(i).getWidth() > 0) {
                sheet.setColumnWidth(i, columnSettings.get(i).getWidth() * 256);
            }
        }
        return columnSettings;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }
}
