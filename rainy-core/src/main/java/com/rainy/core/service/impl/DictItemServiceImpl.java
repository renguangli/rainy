package com.rainy.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.core.entity.DictItem;
import com.rainy.core.mapper.DictItemMapper;
import com.rainy.core.service.DictItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
@Service
public class DictItemServiceImpl
        extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Override
    @Cacheable(cacheNames = "dict-items", key = "#dictCode")
    public List<DictItem> listDictItemsByDictCode(String dictCode) {
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq("dict_code", dictCode);
        return this.baseMapper.selectList(qw);
    }

}
