package com.rainy.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainy.core.entity.DictItem;

import java.util.List;

/**
 * spring-boot-example
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
public interface DictItemService extends IService<DictItem> {

    List<DictItem> listDictItemsByDictCode(String dictCode);

}
