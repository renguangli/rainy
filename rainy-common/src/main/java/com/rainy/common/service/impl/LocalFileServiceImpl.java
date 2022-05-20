package com.rainy.common.service.impl;

import cn.hutool.core.io.FileUtil;
import com.rainy.common.service.FileService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/11 11:56
 */
@Service
public class LocalFileServiceImpl implements FileService {

    @Override
    public void upload(InputStream is, String fullPath) {
        FileUtil.writeFromStream(is, fullPath);
    }

}
