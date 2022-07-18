package com.rainy.power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/13 16:03
 */
@Data
@TableName("t_datasource")
public class Datasource {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String dbType;
    private String url;
    private String username;
    private String password;

}
