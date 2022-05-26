package com.rainy.common.constant;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 字典编码常量类
 *
 * @author renguangli
 * @date 2022/3/23 15:44
 */
public interface DictCodeConstants {

    /** http 安全响应头字典编码 */
    String DICT_SECURITY_RESPONSE_HEADER_CODE = "SYS_SECURITY_RESPONSE_HEADER";

    /** 菜单类型 */
    String MENU_TYPE_BUTTON = "button";
    /** 菜单打开类型  */
    Object[] MENU_TARGET_TYPES = {"_component", "_self", "_blank"};

    String CONFIG_CATEGORY_SA_TOKEN = "SA_TOKEN";
    String CONFIG_CATEGORY_SSO = "SA_SSO";

}
