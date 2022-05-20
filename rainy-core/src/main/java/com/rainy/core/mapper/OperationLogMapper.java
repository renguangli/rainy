package com.rainy.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainy.core.entity.OperationLog;
import org.apache.ibatis.annotations.Update;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/21 11:55
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    @Update("truncate table t_operation_log")
    void clear();
}
