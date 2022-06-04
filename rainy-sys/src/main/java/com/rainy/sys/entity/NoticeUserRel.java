package com.rainy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通知公告用户关系实体(给那些用户接收)
 *
 * @author renguangli
 * @date 2022/4/28 09:28
 */
@Data
@TableName("t_notice")
public class NoticeUserRel {

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("通知公告id")
    private Integer  noticeId;

    @ApiModelProperty("通知人id")
    private Integer userId;

    @ApiModelProperty("是否已读")
    private boolean read;

}
