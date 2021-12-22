package com.wl.study.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author wangliang
 * @since 2021-12-08
 */
@ApiModel(value = "SProduct对象", description = "商品表")
public class SProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "价格")
    private Double price;

    public SProduct() {

    }

    public SProduct(Long id, String name, String code, Double price) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public SProduct(String name, String code, Double price) {
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
