package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 10:27
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DirTypeAndCodeRequestVo {

    @ApiModelProperty(value = "目录类型", required = true)
    @NotNull(message = "目录类型不能为空")
    private String type;

    @ApiModelProperty(value = "目录编码", required = true)
    @NotEmpty(message = "目录编码不能为空")
    private String code;

}
