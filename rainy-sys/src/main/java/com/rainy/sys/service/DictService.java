package com.rainy.sys.service;

import com.rainy.sys.entity.Dict;

import java.util.Map;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface DictService extends BaseService<Dict> {

    Map<String, Map<String, Object>> getDictTree() ;
    boolean deleteDictAndItemsById(Integer id) ;

}
