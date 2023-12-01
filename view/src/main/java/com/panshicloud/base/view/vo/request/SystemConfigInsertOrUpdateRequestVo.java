package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemConfigInsertOrUpdateRequestVo {

    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "编码", required = true)
    @NotEmpty(message = "编码不能为空")
    private String code;

    @ApiModelProperty(value = "值")
    private String value;

}
