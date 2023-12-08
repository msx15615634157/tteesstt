package com.panshicloud.base.view.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wanglibin
 */
@ApiModel("应用响应对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class AppResponseVo implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("应用编码")
    private String code;

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("应用描述")
    private String description;

    @ApiModelProperty("url地址")
    private String url;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
