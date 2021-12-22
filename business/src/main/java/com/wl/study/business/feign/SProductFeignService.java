package com.wl.study.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "wl-study-product")
public interface SProductFeignService {

    @RequestMapping(value = "/api/admin/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void save();

    @RequestMapping(value = "/api/admin/hello", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello();
}
