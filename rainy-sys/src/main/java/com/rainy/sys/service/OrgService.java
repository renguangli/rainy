package com.rainy.sys.service;

import com.rainy.sys.entity.Org;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022 /3/16 19:34
 */
public interface OrgService extends BaseService<Org> {

    /**
     * 生成组织机构树
     *
     * @return the list
     */
    List<Org> getOrgTree();

    /**
     * 获取子节点列表
     *
     * @param id the id
     * @return the list
     */
    List<Org> listOrgsById(Integer id);

    /**
     * 获取子节点id列表
     *
     * @param id the id
     * @return the list
     */
    List<Integer> listOrgIdsById(Integer id);

}
