package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 执行计划实体类
 *
 * @author renguangli
 * @date 2022/7/18 16:41
 */
@Data
@TableName("t_execute_plan")
public class ExecutePlan {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String cron;

}
