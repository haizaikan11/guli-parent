package com.wulx.guli.service.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.utils.StringUtils;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.ResultCodeEnum;
import com.wulx.guli.service.vod.service.VideoService;
import com.wulx.guli.service.vod.util.AliyunVodSDKUtils;
import com.wulx.guli.service.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.InputStream;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {


        // aliyun视频上传所需数据存储对象
        @Autowired
        private VodProperties vodProperties;

        @Override
        public String uploadVideo(InputStream inputStream, String originalFilename) {
            // 视频标题，这里使用视频原文件名作为标题
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // 获取请求对象
            UploadStreamRequest request = new UploadStreamRequest(
                    vodProperties.getKeyId(),
                    vodProperties.getKeySecret(),
                    title,
                    originalFilename,
                    inputStream);

            /* 模板组ID(可选) */
//        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
            /* 工作流ID(可选) */
//        request.setWorkflowId(vodProperties.getWorkflowId());
            // 创建客户端对象，用于发送请求，接收响应
            UploadVideoImpl uploader = new UploadVideoImpl();
            // 发送请求 并接收响应
            UploadStreamResponse response = uploader.uploadStream(request);
            // 获取到视频的id值
            String videoId = response.getVideoId();
            //业务没有正确的返回videoid则说明上传失败
            if(StringUtils.isEmpty(videoId)){
                log.error("阿里云上传失败：" + response.getCode() + " - " + response.getMessage());
                // 这里抛出的异常是已经连上阿里云，但是没能下载到videoId。这样无法，保存进数据库并下次调用，相当于失败
                throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
            }

            return videoId;
        }

    @Override
    public void removeVideo(String videoSourceId) throws ClientException {
            // 获取client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(vodProperties.getKeyId(),
                vodProperties.getKeySecret());

        // 创建request
        DeleteVideoRequest request = new DeleteVideoRequest();
        // 发送请求并获取响应对象
        request.setVideoIds(videoSourceId);

        client.getAcsResponse(request);
    }

    /**
     * 获取视频播放凭证
     * @param videoSourceId
     * @return
     */
    @Override
    public String getPlayAuth(String videoSourceId) throws ClientException {
        // 获取client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(vodProperties.getKeyId(),
                vodProperties.getKeySecret());

        GetVideoPlayAuthRequest videoPlayAuthRequest = new GetVideoPlayAuthRequest();
        videoPlayAuthRequest.setVideoId(videoSourceId);

        GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(videoPlayAuthRequest);
        return acsResponse.getPlayAuth();
    }
}
