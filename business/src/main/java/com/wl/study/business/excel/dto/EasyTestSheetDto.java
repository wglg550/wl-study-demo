package com.wl.study.business.excel.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.wl.study.business.excel.basic.BasicExcelRow;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@ColumnWidth(value = 30)
@HeadStyle(fillForegroundColor = 11)
@HeadFontStyle(color = 1)
public class EasyTestSheetDto extends BasicExcelRow implements Serializable {

    @ExcelProperty(value = "日期")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "日期不能为空")
    String time;

    @ExcelProperty(value = "编码")
    @NotBlank(message = "编码不能为空")
    @Length(message = "编码不能少于{min}位", min = 6)
    @Length(message = "编码不能大于{min}位", max = 20)
    String code;

    @ExcelProperty(value = "年龄")
    @NotBlank(message = "编码不能为空")
    @Min(value = 1, message = "年龄不能小于0")
    @Max(value = 200, message = "年龄不能大于200")
    Integer age;

    @ExcelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    String name;

    @ExcelProperty(value = "分数")
    @DecimalMin(value = "0.00", message = "分数不能小于0")
    Double score;

    @ExcelProperty(value = "考场")
    String exam;

    @ExcelProperty(value = "批次")
    String batchNo;

    @ExcelProperty(value = "场次")
    String activityNo;

    @ExcelProperty(value = "扩展字段")
    String extend;

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
