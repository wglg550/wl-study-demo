package com.wl.study.business.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.wl.study.business.excel.basic.BasicExcelRow;

import java.io.Serializable;

@ColumnWidth(value = 30)
@HeadStyle(fillForegroundColor = 11)
@HeadFontStyle(color = 1)
public class EasyExcelStudentDto extends BasicExcelRow implements Serializable {

    @ExcelProperty(value = "id")
//    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
    String id;

    @ExcelProperty(value = "学校名称")
    String schoolName;

    @ExcelProperty(value = "学校编码")
    String schoolCode;

    @ExcelProperty(value = "编码")
    String code;

    @ExcelProperty(value = "准考证号")
    String examNumber;

    @ExcelProperty(value = "身份证")
    String idcard;

    @ExcelProperty(value = "学号")
    String studentNo;

    @ExcelProperty(value = "科目编码")
    String courseCode;

    @ExcelProperty(value = "科目名称")
    String courseName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
