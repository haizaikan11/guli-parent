package com.wulx.guli.service.oss.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
   private String endpoint;
    private String keyId;
    private String keySecret;
    private String  bucket;
    private String bucketName;
}
