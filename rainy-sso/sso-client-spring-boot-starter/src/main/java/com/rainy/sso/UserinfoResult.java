package com.rainy.sso;

import lombok.Data;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/19 18:37
 */
@Data
public class UserinfoResult {
    private int code;
    private boolean success;
    private String message;
    private Userinfo data;
}
