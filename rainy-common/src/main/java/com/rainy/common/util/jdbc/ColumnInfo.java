package com.rainy.common.util.jdbc;

import lombok.Data;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/7/12 14:50
 */
@Data
public class ColumnInfo {

    private Integer order;
    private String name;
    private String remarks;
    private String dataType;
    private String nullable;

}
