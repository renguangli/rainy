package com.rainy.sys.service.impl;

import com.rainy.sys.service.FileService;

import java.io.InputStream;

/**
 * minio
 *
 * @author renguangli
 * @date 2022/4/11 11:56
 */
//@Service("minioFileService")
public class MinioFileServiceImpl implements FileService {

    @Override
    public void upload(InputStream is, String fullPath) {
        // todo upload file to minio
    }

}
