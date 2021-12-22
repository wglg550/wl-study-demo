package com.wl.study.business.excel.dto;

import com.wl.study.business.annotation.ExcelNote;

import java.io.Serializable;

public class ExcelStudentDto implements Serializable {

    @ExcelNote(value = "id")
    String id;

    @ExcelNote(value = "学校名称")
    String schoolName;

    @ExcelNote(value = "学校编码")
    String schoolCode;

    @ExcelNote(value = "编码")
    String code;

    @ExcelNote(value = "准考证号")
    String examNumber;

    @ExcelNote(value = "身份证")
    String idcard;

    @ExcelNote(value = "学号")
    String studentNo;

    @ExcelNote(value = "科目编码")
    String courseCode;

    @ExcelNote(value = "科目名称")
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
