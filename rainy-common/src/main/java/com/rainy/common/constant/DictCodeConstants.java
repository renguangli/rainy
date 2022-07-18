package com.rainy.common.constant;

/**
 * 字典编码常量类
 *
 * @author renguangli
 * @date 2022/3/23 15:44
 */
public interface DictCodeConstants {

    /** http 安全响应头字典编码 */
    String SECURITY_RESPONSE_HEADER_CODE = "SYS_SECURITY_RESPONSE_HEADER";
    String HTTP_ALLOW_METHOD = "SYS_HTTP_ALLOW_METHOD";

    /** 菜单类型 */
    String MENU_TYPE_BUTTON = "button";
    /** 菜单打开类型  */
    Object[] MENU_TARGET_TYPES = {"_component", "_self", "_blank"};

    String CONFIG_CATEGORY_SA_TOKEN = "SA_TOKEN";
    String CONFIG_CATEGORY_SSO = "SA_SSO";
    String CONFIG_CATEGORY_FRONT = "front";

    /** app应用状态，启用 */
    int APP_STATUS_ENABLE = 0;
    /** app应用状态，禁用 */
    int APP_STATUS_DISABLE = 1;

}
