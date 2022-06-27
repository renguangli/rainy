package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.Resource;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/27 15:28
 */
public interface ResourceService extends IService<Resource> {

    List<Resource> listTree();

}
