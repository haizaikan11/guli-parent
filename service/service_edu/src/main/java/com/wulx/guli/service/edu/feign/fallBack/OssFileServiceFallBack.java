package com.wulx.guli.service.edu.feign.fallBack;

import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 该类是当服务调用失败时，转到该容错类的方法
 */
@Slf4j
@Service
public class OssFileServiceFallBack implements OssFileService {
    @Override
    public R test() {
        return null;
    }

    @Override
    public R removeFile(String url) {
        log.info("服务调用失败");
        return R.error();
    }
}
