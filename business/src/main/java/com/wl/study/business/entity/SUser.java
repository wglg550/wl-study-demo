package com.wl.study.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * ç”¨æˆ·è¡¨
 * </p>
 *
 * @author wangliang
 * @since 2021-11-26
 */
public class SUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    private String national;

    private String name;

    private String password;

    private Integer age;

    private Boolean sex;

    private String address;

    private Integer qq;

    private String wechat;

    private String phone;

    private String email;

    public SUser() {

    }

    public SUser(Long id, String national, String name, String password, Integer age, Boolean sex, String address, Integer qq, String wechat, String phone, String email) {
        this.id = id;
        this.national = national;
        this.name = name;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.qq = qq;
        this.wechat = wechat;
        this.phone = phone;
        this.email = email;
    }

    public SUser(String national, String name, String password, Integer age, Boolean sex, String address, Integer qq, String wechat, String phone, String email) {
        this.national = national;
        this.name = name;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.qq = qq;
        this.wechat = wechat;
        this.phone = phone;
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
