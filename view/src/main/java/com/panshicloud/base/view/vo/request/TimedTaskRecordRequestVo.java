package com.panshicloud.base.view.vo.request;

import com.panshicloud.common.base.request.PageRequestVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务请求参数类
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class TimedTaskRecordRequestVo extends PageRequestVo implements Serializable {

    @ApiModelProperty(value = "定时任务主键", required = true)
    @NotBlank(message = "定时任务主键不能未空")
    private String taskId;

    @ApiModelProperty(value = "开始日期", required = true)
    private Date startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;

    @ApiModelProperty(value = "任务执行状态", required = true)
    private String status;

}
