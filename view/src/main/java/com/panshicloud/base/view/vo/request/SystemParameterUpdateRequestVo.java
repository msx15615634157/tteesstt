package com.panshicloud.base.view.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemParameterUpdateRequestVo {

    @ApiModelProperty(value = "分组编码", required = true)
    @NotEmpty(message = "分组编码不能为空")
    private String groupCode;

    @ApiModelProperty("参数")
    private List<SystemParameter> parameters;

    @Data
    public static class SystemParameter implements Serializable {
        @ApiModelProperty(value = "编码", required = true)
        @NotEmpty(message = "编码不能为空")
        private String code;

        @ApiModelProperty(value = "值", required = true)
        @NotEmpty(message = "值不能为空")
        private String value;
    }
}
