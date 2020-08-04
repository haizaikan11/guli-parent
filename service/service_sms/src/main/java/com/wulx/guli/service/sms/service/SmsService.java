package com.wulx.guli.service.sms.service;

import com.aliyuncs.exceptions.ClientException;

public interface SmsService {

    /**
     * 发送短信
     * @param mobile 电话号码
     * @param checkCode 验证码
     * @throws ClientException
     */
    void send(String mobile, String checkCode) throws ClientException;
}
