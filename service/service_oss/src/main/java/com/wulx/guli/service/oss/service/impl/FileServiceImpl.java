package com.wulx.guli.service.oss.service.impl;

import com.aliyun.oss.OSS;

import com.aliyun.oss.OSSClientBuilder;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.wulx.guli.service.oss.service.FileService;
import com.wulx.guli.service.oss.utils.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.JodaTimeContext;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssProperties ossProperties;

    @Override
    public String upLoad(InputStream inputStream, String module, String oFileName) {
        String endpoint = ossProperties.getEndpoint();

        String bucketName = ossProperties.getBucketName();
        String keyId = ossProperties.getKeyId();
        String keySecret = ossProperties.getKeySecret();
        OSS oss = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        //判断是否已存在实例，如果不存在就创建
        if(!oss.doesBucketExist(bucketName)){
            Bucket bucket = oss.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            oss.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        //1.通过日期创建文件夹进行分类
        String folder = new DateTime().toString("yyyy/MM/dd");
        //2.获取到上传文件的后缀名
        String fileExtension = oFileName.substring(oFileName.lastIndexOf("."));
        //给文件设置新的名称
        String fileName = UUID.randomUUID().toString();
        //3.前面的模块名
         String key =   module +'/' + folder + "/" + fileName + fileExtension;
        //文件上传至阿里云
        oss.putObject(ossProperties.getBucketName(), key, inputStream);

        // 关闭OSSClient。
        oss.shutdown();

        //返回url地址
        return "https://" + bucketName + "." + endpoint + "/" + key;
    }

    /**
     * 根据url删除头像
     * @param url   图片保存在aliyun的完整地址
     *
     */
    @Override
    public void removeFile(String url) {
        //生成的地址名
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyId();
        String keysecret = ossProperties.getKeySecret();
        //bucket实例名
        String bucketname = ossProperties.getBucketName();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        // 去掉url前的地址栏,截取后面的地址
        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());

        // 删除文件。
        ossClient.deleteObject(bucketname, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
