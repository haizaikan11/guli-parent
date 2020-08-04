package com.wulx.guli.service.sms.comtroller.api;


import com.aliyuncs.exceptions.ClientException;
import com.wulx.guli.common.util.FormUtils;
import com.wulx.guli.common.util.RandomUtils;
import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.base.result.ResultCodeEnum;
import com.wulx.guli.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@CrossOrigin //跨域
@Slf4j
public class ApiSmsController {
    @Autowired
    SmsService smsService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("发送验证码")
    @GetMapping("send/{mobile}")
    public R send(@ApiParam(value = "手机号码" ,required = true)
                  @PathVariable String mobile){
        // 验证手机号是否合法
        if (StringUtils.isEmpty(mobile) || !FormUtils.isMobile(mobile)){
            return R.setResult(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }
        try {
            // 生成随机的4位数
            String random = RandomUtils.getFourBitRandom();
            // 发送验证码
            smsService.send(mobile,random);

            //将验证码存入redis缓存
            String key = "checkCode::" + mobile;
            redisTemplate.opsForValue().set(key, random, 5, TimeUnit.MINUTES);
        } catch (ClientException e) {
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
        }
        return R.ok();
    }
}
