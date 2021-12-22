package com.wl.study.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wl.study"})
@MapperScan("com.wl.study.business.mapper")
//主要就是定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中,做过web开发的同学一定都有用过@Controller，@Service，@Repository注解，查看其源码你会发现，他们中有一个共同的注解@Component，没错@ComponentScan注解默认就会装配标识了@Controller，@Service，@Repository，@Component注解的类到spring容器中
@EntityScan(basePackages = {"com.wl.study.business.entity"}) // 用来扫描和发现指定包及其子包中的Entity定义
@EnableTransactionManagement // spring开启事务支持
@EnableAsync // 开启异步任务
@EnableCaching // 开启缓存注解
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wl.study.business.feign")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
