package com.wl.study.business.excel.basic;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class BasicExcelRow implements Serializable {

    @ApiModelProperty(value = "sheet页")
    @ExcelIgnore
    public Integer sheet;

    @ApiModelProperty(value = "行号")
    @ExcelIgnore
    public Integer row;

    @ApiModelProperty(value = "列名和错误原因")
    @ExcelIgnore
    public List<String> errorMessage;

    public Integer getSheet() {
        return sheet;
    }

    public void setSheet(Integer sheet) {
        this.sheet = sheet;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
