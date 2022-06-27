package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Org;
import com.rainy.sys.entity.Resource;
import com.rainy.sys.mapper.ResourceMapper;
import com.rainy.sys.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/27 15:30
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl  extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    public List<Resource> listTree() {
        List<Resource> resources = this.list();
        // 1.找出根结点
        List<Resource> rootResources = resources.stream()
                .filter(resource -> resource.getParentId() == 0)
                .collect(Collectors.toList());
        // 2.为根结点设置子节点
        rootResources.forEach(resource -> {
            resource.setChildren(getChildren(resource.getId(), resources));
        });
        return rootResources;
    }

    private List<Resource> getChildren(Integer parentId, List<Resource> resources) {
        List<Resource> children = new ArrayList<>();
        // 找出子节点
        resources.forEach(resource -> {
            if (parentId.equals(resource.getParentId())) {
                children.add(resource);
            }
        });
        // 递归设置子节点的子节点
        children.forEach(resource -> resource.setChildren(getChildren(resource.getId(), resources)));
        return children.isEmpty() ? null: children;
    }

}
