package com.wl.study.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.study.business.entity.SProduct;
import com.wl.study.business.mapper.SProductMapper;
import com.wl.study.business.service.SProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2021-12-08
 */
@Service
public class SProductServiceImpl extends ServiceImpl<SProductMapper, SProduct> implements SProductService {

    @Override
    public void save() {
        SProduct sProduct = new SProduct("商品1", "test1", 18.00D);
        this.save(sProduct);
    }
}
