package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Dict;
import com.rainy.sys.entity.DictItem;
import com.rainy.sys.mapper.DictItemMapper;
import com.rainy.sys.mapper.DictMapper;
import com.rainy.sys.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:56
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl
        extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictItemMapper dictItemMapper;

    @Override
    public Map<String, Map<String, Object>> getDictTree() {
        Map<String, Map<String, Object>> result = new HashMap<>();
        List<Dict> dicts = this.list();

        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.orderByAsc("sort");
        List<DictItem> list = dictItemMapper.selectList(qw);

        dicts.forEach(d -> {
            Map<String, Object> dict = new LinkedHashMap<>();
            dict.put("name", d.getName());
            dict.put("type", d.getType());
            dict.put("items", new LinkedHashMap<>());
            result.put(d.getCode(), dict);
        });

        list.stream()
                .collect(Collectors.groupingBy(DictItem::getDictCode))
                .forEach((key, value) -> {
                    Map<String, Object> items = new LinkedHashMap<>();
                    value.forEach(item -> items.put(item.getCode(), item.getValue()));
                    Map<String, Object> data = result.get(key);
                    if (data != null) {
                        data.put("items", items);
                    }
                });
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDictAndItemsById(Integer id) {
        Dict dict = this.baseMapper.selectById(id);
        // 字典不存在时直接返回
        if (dict == null) {
            return false;
        }
        // 1.删除字典
        this.baseMapper.deleteById(id);
        // 2.删除字典项
        QueryWrapper<DictItem> qw = new QueryWrapper<>();
        qw.eq("dict_code", dict.getCode());
        dictItemMapper.delete(qw);
        return true;
    }

    @Override
    public boolean exists(String column, String value) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

    @Override
    public boolean exists(Integer id, String column, String value) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.ne("id", id);
        qw.eq(column, value);
        return this.baseMapper.exists(qw);
    }

}
