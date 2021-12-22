package com.wl.study.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.study.business.entity.SUser;

/**
 * <p>
 * ç”¨æˆ·è¡¨ 服务类
 * </p>
 *
 * @author wangliang
 * @since 2021-11-26
 */
public interface SUserService extends IService<SUser> {

    public void save();
}
