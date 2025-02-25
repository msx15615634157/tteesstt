package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * zhangze
 */
@ApiModel("分组参数响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupTimedParameterResponseVo implements Serializable {

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("方法标识")
    private String methodCode;
}
