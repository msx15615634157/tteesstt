package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanglibin
 * @date 2023/6/15
 */
@Data
@ApiModel
public class MenuParameterResponseVo {

    @ApiModelProperty("参数编码")
    private String code;

    @ApiModelProperty("参数名称")
    private String name;

    @ApiModelProperty("参数值")
    private Object value;

    @ApiModelProperty("是否扩展")
    private String isTab;

}
