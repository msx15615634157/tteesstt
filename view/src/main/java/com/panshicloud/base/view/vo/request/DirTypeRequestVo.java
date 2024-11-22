package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/29 14:24
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DirTypeRequestVo {

    @ApiModelProperty(value = "目录类型", required = true)
    @NotNull(message = "目录类型不能为空")
    private Integer type;

}
