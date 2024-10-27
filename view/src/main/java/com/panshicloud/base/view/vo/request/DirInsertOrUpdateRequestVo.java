package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 11:25
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DirInsertOrUpdateRequestVo {

    @ApiModelProperty(value = "编码", required = true)
    @NotEmpty(message = "id不能为空")
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "类型", required = true)
    @NotEmpty(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "父级编码", required = true)
    private String parentCode;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

}
