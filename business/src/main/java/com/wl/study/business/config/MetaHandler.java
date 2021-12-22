package com.wl.study.business.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Description: mybatis 自动插入时间和id
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/20
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", System.currentTimeMillis(), metaObject);
        this.setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
    }
}
