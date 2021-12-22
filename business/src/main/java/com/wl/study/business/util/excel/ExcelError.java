package com.wl.study.business.util.excel;

import java.io.Serializable;

/**
 * @Description: excel导入错误
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/5/20
 */
public class ExcelError implements Serializable {

    /**
     * 错误行数
     */
    private int row;

    /**
     * 错误类型
     */
    private String excelErrorType;

    public ExcelError() {

    }

    public ExcelError(int row, String excelErrorType) {
        this.row = row;
        this.excelErrorType = excelErrorType;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getExcelErrorType() {
        return excelErrorType;
    }

    public void setExcelErrorType(String excelErrorType) {
        this.excelErrorType = excelErrorType;
    }
}
