package com.wulx.guli.service.oss.controller.admin;

import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.base.result.ResultCodeEnum;
import com.wulx.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     */
    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module)  {

        String uploadUrl = null;
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            uploadUrl = fileService.upLoad(inputStream, module, originalFilename);
        } catch (Exception e) {
            //抛出自定义异常，在这给用户看的只需要最终的结果，即文件上传异常
           throw  new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        //返回r对象
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }


    @DeleteMapping("remove")
    @ApiOperation("删除头像")
    public R removeFile(
            @ApiParam(value = "要删除的文件路径",required = true)
            @RequestBody
            String url){
        log.info("删除功能触发");
        fileService.removeFile(url);
        return R.ok().message("删除文件成功");
    }


    @ApiOperation("测试微服务调用")
    @GetMapping("test")
    public R test(){

        //测试超时，openFeign的机制是会请求二次，等待一秒。如果两次在一秒内都没有收到响应，则会返回异常
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Oss微服务被调用");
        //如果上面异常，会被异常类捕获，返回一个
        return R.ok();
    }
}
