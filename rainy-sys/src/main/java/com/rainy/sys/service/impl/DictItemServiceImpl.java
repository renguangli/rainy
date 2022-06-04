package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.DictItem;
import com.rainy.sys.mapper.DictItemMapper;
import com.rainy.sys.service.DictItemService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = "rainy:dict-items")
public class DictItemServiceImpl
        extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Override
    @Cacheable(key = "#dictCode")
    public List<DictItem> listByDictCode(String dictCode) {
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq("dict_code", dictCode);
        return this.baseMapper.selectList(qw);
    }

    @Override
    @CacheEvict(key = "#dictItem.dictCode")
    public boolean save(DictItem dictItem) {
        return this.baseMapper.insert(dictItem) > 0;
    }

    @Override
    @CacheEvict(key = "#dictItem.dictCode")
    public boolean updateById(DictItem dictItem) {
        return this.baseMapper.updateById(dictItem) > 0;
    }

}
