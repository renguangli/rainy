package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.Dict;

import java.util.Map;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface DictService extends IService<Dict> {

    Map<String, Map<String, Object>> getDictTree() ;
    boolean deleteDictAndItemsById(Integer id) ;
}
