package com.wl.study.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wl.study.business.feign.SProductFeignService;
import com.wl.study.business.service.SUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: seata
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/11/26
 */
@Api(tags = "seataController")
@RestController
@RequestMapping("/${prefix.url.seata}")
public class SeataController {
    private final static Logger log = LoggerFactory.getLogger(SeataController.class);

    @Resource
    SUserService sUserService;

    @Resource
    SProductFeignService sProductFeignService;

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    @SentinelResource(value = "test", fallback = "saveFallback")
    public String save() {
        sUserService.save();
        return null;
    }

    @ApiOperation(value = "hello")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    @SentinelResource(value = "hello", fallback = "helloFallback")
    public String hello() {
        return sProductFeignService.hello();
    }

    // 自定义异常必须与原方法返回值及参数完全一致
    public String helloFallback(Throwable e) {
        return "hello异常了！";
    }

    // 自定义异常必须与原方法返回值及参数完全一致
    public String saveFallback(Throwable e) {
        return "save异常了！";
    }
}
