package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 *  查枚举请求vo
 * </p>
 *
 * @author huangrankun
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class DataOptionIdRequestVo implements Serializable {
    @ApiModelProperty(value = "领域", required = true)
    @NotEmpty(message = "领域不能为空")
    private String domain;

    @ApiModelProperty(value = "字典编码", required = true)
    @NotEmpty(message = "字典编码不能为空")
    private String code;
}
