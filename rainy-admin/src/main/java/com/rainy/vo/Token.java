package com.rainy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token
 *
 * @author renguangli
 * @date 2022/3/29 18:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String tokenName;
    private String tokenValue;
    private long tokenTimeout;
}
