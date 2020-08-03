package com.wulx.guli.service.edu.service.impl;

import com.wulx.guli.service.edu.entity.Video;
import com.wulx.guli.service.edu.feign.VodMediaService;
import com.wulx.guli.service.edu.mapper.VideoMapper;
import com.wulx.guli.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    VodMediaService vodMediaService;

    @Override
    public void removeMediaVideoById(String id) {
        Video video = baseMapper.selectById(id);
        if(video != null){
            vodMediaService.removeVideo(id);
        }

    }
}
