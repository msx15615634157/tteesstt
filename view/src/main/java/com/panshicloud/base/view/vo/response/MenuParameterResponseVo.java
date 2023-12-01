package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MenuParameterResponseVo {

    @ApiModelProperty("参数编码")
    private String code;

    @ApiModelProperty("参数值")
    private Object value;

}
