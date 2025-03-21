package com.panshicloud.base.view.vo.request;

import com.panshicloud.base.remote.dto.MenuParameterDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单新增请求vo
 * </p>
 *
 * @author yantao
 */

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuInsetRequestVo {

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "描述", required = true)
    private String description;

    @ApiModelProperty(value = "菜单编码", required = true)
    @NotEmpty(message = "菜单编码不能为空")
    private String code;

    @ApiModelProperty(value = "所属应用", required = true)
    @NotEmpty(message = "所属应用不能为空")
    private String appId;


    @ApiModelProperty(value = "菜单类型", required = true)
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "父级菜单", required = true)
    private String parentId;

    @ApiModelProperty(value = "菜单图标", required = true)
    private String icon;

    @ApiModelProperty(value = "权重值", required = true)
    private Integer currentSort;

    @ApiModelProperty(value = "路由名称", required = true)
    @NotEmpty(message = "路由名称不能为空")
    private String routerName;

    @ApiModelProperty(value = "菜单参数", required = true)
    private List<MenuParameterDto> param;

    @ApiModelProperty(value = "权重值", required = true)
    @NotNull(message = "权重值不能为空")
    private Integer sort;

}
