package com.rainy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.sys.entity.DictItem;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface DictItemService extends IService<DictItem> {

    List<DictItem> listByDictCode(String dictCode);

}
