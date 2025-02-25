package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xingxingfa
 * @since 2024/11/7
 */
@ApiModel("菜单和应用响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuAndAppResponseVo implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("应用编码")
    private String code;

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("应用描述")
    private String description;

    @ApiModelProperty("url地址")
    private String url;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("菜单集合")
    private List<MenuResponseVo> menuList;
}
