package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@ApiModel("系统参数响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemConfigResponseVo implements Serializable {


    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("值")
    private Object value;

}
