package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xingxingfa
 * @since 2024/10/28
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DirUpdateRequestVo {

    @ApiModelProperty(value = "编码", required = true)
    @NotEmpty(message = "编码不能为空")
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "类型", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;

}
