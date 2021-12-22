package com.wl.study.business.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Description: redis config
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/16
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(60L)
//                , // 默认策略，未配置的 key 会使用这个
//                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

//    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
//        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
//        redisCacheConfigurationMap.put(SystemConstant.userOauth,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.studentOauth,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.userAccount,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.studentAccount,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.orgCache,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.orgCodeCache,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.roleCache,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        redisCacheConfigurationMap.put(SystemConstant.configCache,
//                this.getRedisCacheConfigurationWithTtl(SystemConstant.REDIS_EXPIRE_TIME));
//        return redisCacheConfigurationMap;
//    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Long seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(seconds))
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> cacheName + ":");
        return redisCacheConfiguration;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}