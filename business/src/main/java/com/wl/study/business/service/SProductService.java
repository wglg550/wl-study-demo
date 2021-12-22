package com.wl.study.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.study.business.entity.SProduct;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2021-12-08
 */
public interface SProductService extends IService<SProduct> {

    public void save();
}
