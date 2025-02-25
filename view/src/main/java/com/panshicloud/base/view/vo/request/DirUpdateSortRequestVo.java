package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xingxingfa
 * @since 2024/11/8
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DirUpdateSortRequestVo implements Serializable {

    @ApiModelProperty(value = "当前目录编码", required = true)
    @NotEmpty(message = "当前目录编码不能为空")
    private String code;

    @ApiModelProperty(value = "父级目录编码")
    private String parentCode;

    @ApiModelProperty(value = "类型", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "排序字段", required = true)
    @NotNull(message = "排序字段不能为空")
    private Integer sort;

}
