package com.wl.study.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.study.business.entity.SUser;
import com.wl.study.business.feign.SProductFeignService;
import com.wl.study.business.mapper.SUserMapper;
import com.wl.study.business.service.SUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * ç”¨æˆ·è¡¨ 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2021-11-26
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements SUserService {

    @Resource
    SProductFeignService sProductFeignService;

    @GlobalTransactional
    @Override
    public void save() {
        SUser sUser = new SUser("中国", "测试1", "123456", 20, true, "测试地址1", 111111, "222222", "18008659123", "111111qq.com");
        this.save(sUser);

        sProductFeignService.save();
//        int i = 1/0;
    }
}
