package com.wl.study.product.controller;

import com.wl.study.business.service.SProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author wangliang
 * @since 2021-12-08
 */
@Api(tags = "productController")
@RestController
@RefreshScope
@RequestMapping("/${prefix.url.admin}")
public class SProductController {

    @Resource
    SProductService sProductService;

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public void save() {
        sProductService.save();
    }

    @ApiOperation(value = "hello")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public String hello() {
        return "hello world!";
    }
}
