package com.wulx.guli.service.oss.service;


import java.io.InputStream;

public interface FileService {

    /**
     *
     * @param inputStream  文件流
     * @param module  模块名
     * @param oFileName  原始文件名
     * @return  文件在oss上的完整url
     */
    String upLoad(InputStream inputStream,String module,String oFileName);
    void removeFile(String url);
}
