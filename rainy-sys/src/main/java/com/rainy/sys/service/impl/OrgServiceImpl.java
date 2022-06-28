package com.rainy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainy.sys.entity.Org;
import com.rainy.sys.mapper.OrgMapper;
import com.rainy.sys.service.OrgService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/16 19:34
 */
@Service
public class OrgServiceImpl
        extends ServiceImpl<OrgMapper, Org> implements OrgService {

    @Override
    public List<Org> getOrgTree(){
        List<Org> orgs = list();
        // 找出根节点
        List<Org> rootOrgs = orgs.stream()
                .filter(org -> org.getParentId() == null || org.getParentId() == 0)
                .collect(Collectors.toList());
        // 为根节点设置子节点
        rootOrgs.forEach(org -> {
            org.setChildren(getChildren(org.getId(), orgs));
        });
        return rootOrgs;
    }

    /**
     * 获取指定id节点以及子节点下的节点列表
     * @param id 组织结构id
     * @return 指定id节点以及子节点下的节点列表
     */
    @Override
    public List<Org> listOrgsById(Integer id) {
        List<Org> orgs = new ArrayList<>();
        // 找出等于id的根节点
        List<Org> orgList = list();
        Optional<Org> orgOptional = orgList.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst();
        if (!orgOptional.isPresent()) {
            return orgs;
        }
        orgs.add(orgOptional.get());
        // 找出 parentId 等于 id 的数据
        List<Org> orgsById = getOrgsById(id, orgList);
        orgs.addAll(orgsById);
        return orgs;
    }

    @Override
    public List<Integer> listOrgIdsById(Integer id) {
        List<Org> orgList = this.listOrgsById(id);
        return orgList.stream()
                .map(Org::getId)
                .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点列表
     * @param parentId parentId
     * @param orgs orgs
     * @return 子节点列表
     */
    private List<Org> getChildren(Integer parentId, List<Org> orgs) {
        List<Org> children = new ArrayList<>();
        // 找出子节点
        orgs.forEach(org -> {
            if (parentId.equals(org.getParentId())) {
                children.add(org);
            }
        });
        // 递归设置子节点的子节点
        children.forEach(org -> org.setChildren(getChildren(org.getId(), orgs)));
        return children;
    }

    /**
     * 递归查找子节点下的节点列表
     * @param parentId 父节点id
     * @param orgs 列表
     * @return 子节点下的节点列表
     */
    private List<Org> getOrgsById(Serializable parentId, List<Org> orgs) {
        List<Org> orgListByParentId = new ArrayList<>();
        // 找出 parentId 等于 org.getParentId() 的数据
        for (Org org : orgs) {
            if (parentId.equals(org.getParentId())) {
                orgListByParentId.add(org);
            }
        }
        List<Org> orgList = new ArrayList<>(orgListByParentId);
        // 递归查找子节点数据
        for (Org org : orgListByParentId) {
            List<Org> orgsById = getOrgsById(org.getId(), orgs);
            if (!orgsById.isEmpty()) {
                // 防止并非修改错误，不能直接使用 orgListByParentId 添加
                orgList.addAll(orgsById);
            }
        }
        return orgList;
    }

}
