package com.panshicloud.base.view.vo.response;

import com.panshicloud.base.remote.dto.MenuParameterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单响应对象
 * </p>
 *
 * @author yantao
 */

@ApiModel("菜单响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuEnablePermissionResponseVo implements Serializable {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单编码")
    private String code;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("菜单类型，0:普通, 1:超链接, 2:预留")
    private Integer type;

    @ApiModelProperty("权重")
    private Integer sort;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("路由名称")
    private String routerName;

    @ApiModelProperty("父级菜单id")
    private String parentId;

    @ApiModelProperty("菜单参数")
    private Map<String, String> param;

    @ApiModelProperty("菜单参数响应集合")
    private List<MenuParameterDto> menuParameters;

}
