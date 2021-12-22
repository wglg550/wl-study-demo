package com.wl.study.user.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
* @Description: es config
* @Param:
* @return:
* @Author: wangliang
* @Date: 2021/12/7
*/
@Configuration
public class EsConfig {

    // 将RestHighLevelClient 修改成这种方式
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restClient() {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("127.0.0.1:9200")
                .build();

        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
        return client;
    }
}