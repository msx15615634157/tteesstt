package com.panshicloud.base.view.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panshicloud.common.base.request.PageRequestVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author xingxingfa
 * @since 2022/12/30
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationLogLikeRequestVo extends PageRequestVo {

    @ApiModelProperty(value = "筛选的开始时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "筛选的结束时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "类型/模块", required = true)
    private String type;

    @ApiModelProperty(value = "操作", required = true)
    private String operation;

    @ApiModelProperty(value = "操作用户id", required = true)
    private String operationUserId;
}
