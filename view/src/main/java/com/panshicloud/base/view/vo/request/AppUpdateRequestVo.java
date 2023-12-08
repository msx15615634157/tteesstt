package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 应用更新请求实体
 * </p>
 *
 * @author wanglibin
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class AppUpdateRequestVo {

    @ApiModelProperty(value = "id", required = true)
    @NotEmpty(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "应用编码", required = true)
    @NotEmpty(message = "应用编码不能为空")
    private String code;

    @ApiModelProperty(value = "应用名称", required = true)
    @NotEmpty(message = "应用名称不能为空")
    private String name;

    @ApiModelProperty(value = "应用描述")
    private String description;

    @ApiModelProperty(value = "url地址")
    private String url;

}
