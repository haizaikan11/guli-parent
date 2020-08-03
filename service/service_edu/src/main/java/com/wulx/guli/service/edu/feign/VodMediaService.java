package com.wulx.guli.service.edu.feign;

import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.feign.fallBack.VodMediaServiceFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "vod-service",fallback = VodMediaServiceFallBack.class)
public interface VodMediaService {
    // 地址需写全！
    @DeleteMapping("/admin/vod/media/remove/{videoSourceId}")
    public R removeVideo(
            @ApiParam(value = "阿里云视频id",required = true)
            @PathVariable String videoSourceId
    );
}
