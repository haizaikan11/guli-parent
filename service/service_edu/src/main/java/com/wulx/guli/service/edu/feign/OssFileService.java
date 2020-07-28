package com.wulx.guli.service.edu.feign;


import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.feign.fallBack.OssFileServiceFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

//当失败时，fallback到指定实现类中的容错类方法执行
@FeignClient(value = "service-oss" ,fallback = OssFileServiceFallBack.class)  //指定微服务，通过微服务名去注册中心获取该微服务地址
public interface OssFileService {
    //被调用的方法的url地址，需要是全路径
    @GetMapping("/admin/oss/file/test")
    public R test();

    @DeleteMapping("/admin/oss/file/remove")
    public R removeFile(@RequestBody String url);
}
