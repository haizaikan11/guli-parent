package com.wulx.guli.service.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wulx.guli.service.cms.entity.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wulx.guli.service.cms.entity.vo.AdVo;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * 广告推荐 Mapper 接口
 * </p>
 *
 * @author wulingxiao
 * @since 2020-08-03
 */
public interface AdMapper extends BaseMapper<Ad> {

    List<AdVo> selectPageByQueryWrapper(
            Page<AdVo> pageParam,
            @Param(Constants.WRAPPER) QueryWrapper<AdVo> queryWrapper);
}

