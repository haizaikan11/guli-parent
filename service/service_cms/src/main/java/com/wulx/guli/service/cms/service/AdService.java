package com.wulx.guli.service.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wulx.guli.service.cms.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wulx.guli.service.cms.entity.vo.AdVo;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-08-03
 */
public interface AdService extends IService<Ad> {

    IPage<AdVo> selectPage(Long page, Long limit);

    boolean removeAdImageById(String id);
}
