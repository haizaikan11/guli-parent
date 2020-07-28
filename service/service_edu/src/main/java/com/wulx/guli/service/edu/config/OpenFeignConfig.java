package com.wulx.guli.service.edu.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//feign的配置类，设置日志的打印级别
@Configuration
public class OpenFeignConfig {
    //设置日志级别
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
