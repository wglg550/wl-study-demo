package com.wl.study.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/11/26
 */
@Api(tags = "用户Controller")
@RestController
@RefreshScope
@RequestMapping("/${prefix.url.admin}")
public class SUserController {
    private final static Logger log = LoggerFactory.getLogger(SUserController.class);

    @ApiOperation(value = "hello")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "保存", response = Object.class)})
    public String hello() {
        return "Hello Sentinel";
    }

    @SentinelResource(value = "test", blockHandler = "flowException")
    @ApiOperation(value = "测试nacos限流")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "限流测试", response = Object.class)})
    public String test() {
        return "Hello I'm nacos_provide flow";
    }

    @SentinelResource(value = "test1", blockHandler = "degradeException")
    @ApiOperation(value = "测试nacos降级")
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "降级测试", response = Object.class)})
    public String test1() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello I'm nacos_provide degrade";
    }

    // 自定义异常必须与原方法返回值及参数完全一致
    public String flowException(BlockException e) {
        return "限流了！";
    }

    // 自定义异常必须与原方法返回值及参数完全一致
    public String degradeException(BlockException e) {
        return "降级了！";
    }

    @Value(value = "${publish}")
    private boolean publish;

    @Value(value = "${value}")
    private String value;

    @Value(value = "${age}")
    private int age;

    @ApiOperation(value = "测试nacos动态服务配置")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "测试nacos动态服务配置", response = Object.class)})
    public Map get() {
        Map map = new HashMap();
        map.computeIfAbsent("publish", v -> publish);
        map.computeIfAbsent("value", v -> value);
        map.computeIfAbsent("age", v -> age);
        return map;
    }
}
