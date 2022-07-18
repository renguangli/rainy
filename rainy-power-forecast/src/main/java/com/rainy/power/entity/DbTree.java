package com.rainy.power.entity;

import lombok.Data;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/13 17:21
 */
@Data
public class DbTree {

    private String key;
    private String name;
    private String dbType;
    private Boolean isLeaf;
    private List<DbTree> children;

    public DbTree(String name) {
        this.name = name;
    }

}
