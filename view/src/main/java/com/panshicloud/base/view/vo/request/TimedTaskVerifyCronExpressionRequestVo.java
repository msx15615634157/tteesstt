package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年05月27日 14:30:44
 * @packageName com.panshicloud.base.view.vo.request
 * @className TimedTaskVerifyCronExpressionRequestVo
 * @describe 校验corn表达式请求
 */
@Data
public class TimedTaskVerifyCronExpressionRequestVo {

    @ApiModelProperty(value = "cron表达式")
    @NotEmpty
    private String cron;
}
