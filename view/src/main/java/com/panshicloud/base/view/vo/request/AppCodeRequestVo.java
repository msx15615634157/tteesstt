package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 按应用编码查找请求类
 * </p>
 *
 * @author yantao
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class AppCodeRequestVo {

    @ApiModelProperty(value = "应用编码", required = true)
    @NotEmpty(message = "应用编码不能为空")
    private String code;
}
