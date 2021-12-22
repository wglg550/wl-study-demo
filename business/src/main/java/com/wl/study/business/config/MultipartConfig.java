package com.wl.study.business.config;

/**
* @Description: Multipart 附件上传配置
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020/7/29
*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {

    /**
     * 附件上传配置
     *
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(2);// 低于此值，只保留在内存里，超过此阈值，生成硬盘上的临时文件。
        resolver.setMaxUploadSize(200 * 1024 * 1024);// 最大200M
        return resolver;
    }
}
