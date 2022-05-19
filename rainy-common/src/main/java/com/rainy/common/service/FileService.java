package com.rainy.common.service;

import java.io.InputStream;

/**
 * 文件服务
 *
 * @author renguangli
 * @date 2022/4/11 11:56
 */
public interface FileService {

    void upload(InputStream is, String filename);

}
