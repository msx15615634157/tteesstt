package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class TimedTaskUpdateRequestVo implements Serializable {

    @ApiModelProperty(value = "定时任务主键")
    @NotEmpty(message = "定时任务主键不能为空")
    private String id;

    @ApiModelProperty(value = "定时任务编码")
    @NotEmpty(message = "定时任务编码不能为空")
    private String code;

    @ApiModelProperty(value = "定时任务名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "定时任务类型")
    @NotNull(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "定时任务分组类型")
    @NotNull(message = "分组类型不能为空")
    private String groupType;

    @ApiModelProperty(value = "定时任务状态")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @ApiModelProperty(value = "定时任务执行用户")
    @NotEmpty(message = "执行用户不能为空")
    private String operateUserName;

    @ApiModelProperty(value = "定时任务执行频率")
    @NotEmpty(message = "执行频率不能为空")
    private String cornExpression;

    @ApiModelProperty(value = "定时任务备注")
    private String remark;

    @ApiModelProperty(value = "定时任务参数")
    private String configParams;
}
