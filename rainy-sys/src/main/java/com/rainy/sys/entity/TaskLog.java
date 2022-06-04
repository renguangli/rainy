package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainy.common.util.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时任务执行日志实体类
 *
 * @author renguangli
 * @date 2022/3/28 15:27
 */
@Data
@TableName("t_task_log")
public class TaskLog {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField("`group`")
    private String group;
    private String className;
    private String method;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    @OrderBy(asc = false)
    private LocalDateTime datetime;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date nextDatetime;
    private long processTime;
    private boolean success;
    private String errorMessage;

}
