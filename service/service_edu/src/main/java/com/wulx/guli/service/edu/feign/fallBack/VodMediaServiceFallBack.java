package com.wulx.guli.service.edu.feign.fallBack;

import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.feign.VodMediaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VodMediaServiceFallBack implements VodMediaService {
    @Override
    public R removeVideo(String videoSourceId) {
        log.warn("熔断保护");
        return R.error().message("远程调用失败");
    }
}
