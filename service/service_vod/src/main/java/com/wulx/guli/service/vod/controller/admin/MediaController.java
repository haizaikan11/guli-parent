package com.wulx.guli.service.vod.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.base.result.ResultCodeEnum;
import com.wulx.guli.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin.extension.ExtensionUtils;


import java.io.IOException;

@Api(tags = "阿里云视频点播")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/vod/media")
@Slf4j
public class MediaController {
    @Autowired
    VideoService videoService;

    @ApiOperation("上传视频文件")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(value = "视频文件" , required = true)
            @RequestParam  MultipartFile file
    ){
        try {
            String videoId = videoService.uploadVideo(file.getInputStream(), file.getOriginalFilename());
            return R.ok().message("上传成功").data("videoId",videoId);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            //这里的异常是未连接到阿里云的异常
            throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }
    }

    @ApiOperation("根据阿里云视频id删除课时")
    @DeleteMapping("remove/{videoSourceId}")
    public R removeVideo(
            @ApiParam(value = "阿里云视频id",required = true)
           @PathVariable String videoSourceId
    ){
        try {
            videoService.removeVideo(videoSourceId);
            return R.ok().message("视频删除成功");
        } catch (ClientException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            // 删除失败
            throw new GuliException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }
}
