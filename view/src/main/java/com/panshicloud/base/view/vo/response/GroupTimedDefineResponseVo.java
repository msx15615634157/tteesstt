package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@ApiModel("分组定义响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupTimedDefineResponseVo implements Serializable {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;
}
