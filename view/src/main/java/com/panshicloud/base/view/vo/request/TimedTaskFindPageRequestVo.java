package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 定时任务请求参数类
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class TimedTaskFindPageRequestVo implements Serializable {

    @ApiModelProperty(value = "定时任务主键")
    private String id;

    @ApiModelProperty(value = "定时任务编码")
    private String code;

    @ApiModelProperty(value = "定时任务名称")
    private String name;

    @ApiModelProperty(value = "定时任务类型")
    private String type;

    @ApiModelProperty(value = "定时任务分组类型")
    private String groupType;

    @ApiModelProperty(value = "定时任务状态")
    private String status;

    @ApiModelProperty(value = "页数")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;
}
