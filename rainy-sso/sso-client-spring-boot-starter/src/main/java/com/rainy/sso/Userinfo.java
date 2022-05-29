package com.rainy.sso;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/5/19 18:37
 */
@Data
public class Userinfo {

    private String username;
    private String name;
    private String avatar;
    private Long loginCount;
    private String lastLoginTime;
    private String lastLoginIp;
    private String browser;
    private String os;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

}
