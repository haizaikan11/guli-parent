package com.wulx.guli.service.vod.service;


import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;

public interface VideoService {
    /**
     *视频文件上传
     * @param inputStream    file 要上传的视频文件流
     * @param originalFilename  原始文件名带拓展名
     * @return  返回一个阿里云id
     */
    String uploadVideo(InputStream inputStream, String originalFilename);

    void removeVideo(String videoSourceId) throws ClientException;

    String getPlayAuth(String videoSourceId) throws ClientException;
}
