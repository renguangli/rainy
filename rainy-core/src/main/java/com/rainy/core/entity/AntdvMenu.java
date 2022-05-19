package com.rainy.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ant design pro of vue 菜单结构
 *
 * @author renguangli
 * @date 2022/3/11 13:42
 */
@Data
public class AntdvMenu {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("父id")
    private Integer parentId;
    @ApiModelProperty("路由名称")
    private String name;
    @ApiModelProperty("路由地址")
    private String path;
//    @ApiModelProperty("重定向地址")
//    private String redirect;
    @ApiModelProperty("vue 组件")
    private String component;
    private Meta meta;

    @Data
    public static class Meta {
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图标")
        private String icon;
        @ApiModelProperty("内外链url")
        private String url;
        @ApiModelProperty("打开方式")
        private String target;
        @ApiModelProperty("是否展示")
        private Boolean show;
//        @ApiModelProperty("是否展示面包屑")
//        private String hiddenHeaderContent;
//        private String permission;
    }
}
